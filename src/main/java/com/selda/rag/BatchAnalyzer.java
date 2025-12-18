package com.selda.rag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BatchAnalyzer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build();
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    /**
     * Klasördeki tüm desteklenen dosyaları analiz eder
     */
    public static BatchResult analyzeDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Geçersiz klasör yolu: " + directoryPath);
        }

        List<File> files = findSupportedFiles(directory);
        if (files.isEmpty()) {
            System.out.println("Klasörde desteklenen dosya bulunamadı.");
            return new BatchResult();
        }

        System.out.println("Bulunan " + files.size() + " dosya analiz ediliyor...");
        
        List<FileAnalysis> results = new ArrayList<>();
        for (File file : files) {
            try {
                System.out.println("Analiz ediliyor: " + file.getName());
                FileAnalysis analysis = analyzeFile(file);
                results.add(analysis);
                System.out.println("✓ Başarılı: " + file.getName());
            } catch (Exception e) {
                System.err.println("✗ Hata: " + file.getName() + " - " + e.getMessage());
                results.add(new FileAnalysis(file.getName(), null, e.getMessage()));
            }
        }

        return new BatchResult(results);
    }

    /**
     * Belirli dosyaları analiz eder
     */
    public static BatchResult analyzeFiles(List<String> filePaths) throws IOException {
        List<FileAnalysis> results = new ArrayList<>();
        
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Dosya bulunamadı: " + filePath);
                results.add(new FileAnalysis(file.getName(), null, "Dosya bulunamadı"));
                continue;
            }

            try {
                System.out.println("Analiz ediliyor: " + file.getName());
                FileAnalysis analysis = analyzeFile(file);
                results.add(analysis);
                System.out.println("✓ Başarılı: " + file.getName());
            } catch (Exception e) {
                System.err.println("✗ Hata: " + file.getName() + " - " + e.getMessage());
                results.add(new FileAnalysis(file.getName(), null, e.getMessage()));
            }
        }

        return new BatchResult(results);
    }

    /**
     * Tek dosyayı analiz eder
     */
    private static FileAnalysis analyzeFile(File file) throws IOException {
        String content = DocumentReader.readDocument(file.getAbsolutePath());
        
        String prompt = "SADECE bu şemaya uyan geçerli JSON ile yanıt ver:\n" +
                "CRITICAL: TÜM YANITLAR TÜRKÇE OLMALIDIR.\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"string\"],\n" +
                "  \"nonFunctionalRequirements\": [\"string\"],\n" +
                "  \"missingInformation\": [\"string\"],\n" +
                "  \"priorityHints\": [\"string\"]\n" +
                "}\n" +
                "Analiz edilecek gereksinim metni:\n" +
                content;

        String requestBody = MAPPER.createObjectNode()
                .put("model", "llama3:latest")
                .put("prompt", prompt)
                .put("stream", false)
                .toString();

        RequestBody body = RequestBody.create(
                requestBody, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(OLLAMA_URL)
                .post(body)
                .build();

        Response response = CLIENT.newCall(request).execute();
        String responseBody = response.body().string();

        if (response.code() != 200) {
            throw new IOException("Ollama hatası: " + response.code() + " - " + responseBody);
        }

        JsonNode root = MAPPER.readTree(responseBody);
        JsonNode analysisResult = null;
        
        if (root.has("response")) {
            String responseText = root.get("response").asText();
            try {
                // JSON'u bul ve çıkar
                String jsonPart = extractJsonFromResponse(responseText);
                analysisResult = MAPPER.readTree(jsonPart);
            } catch (Exception e) {
                throw new IOException("JSON parse hatası: " + responseText);
            }
        } else {
            throw new IOException("Geçersiz yanıt formatı");
        }

        return new FileAnalysis(file.getName(), analysisResult, null);
    }

    /**
     * Desteklenen dosyaları bulur
     */
    private static List<File> findSupportedFiles(File directory) {
        List<File> files = new ArrayList<>();
        File[] fileList = directory.listFiles();
        
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    String name = file.getName().toLowerCase();
                    if (name.endsWith(".pdf") || name.endsWith(".docx")) {
                        files.add(file);
                    }
                }
            }
        }
        
        return files;
    }

    /**
     * Yanıttan JSON kısmını çıkarır
     */
    private static String extractJsonFromResponse(String responseText) {
        // İlk { karakterini bul
        int startIndex = responseText.indexOf('{');
        if (startIndex == -1) {
            throw new RuntimeException("JSON bulunamadı");
        }
        
        // Son } karakterini bul (en sondan)
        int lastIndex = responseText.lastIndexOf('}');
        if (lastIndex == -1 || lastIndex <= startIndex) {
            // Eğer } yoksa, JSON'u tamamlamaya çalış
            String jsonPart = responseText.substring(startIndex);
            jsonPart = jsonPart.replace("\n", "").replace("\r", "").trim();
            
            // Eğer } ile bitmiyorsa ekle
            if (!jsonPart.endsWith("}")) {
                jsonPart += "}";
            }
            
            return jsonPart;
        }
        
        // JSON kısmını çıkar
        String jsonPart = responseText.substring(startIndex, lastIndex + 1);
        
        // Satır sonlarını temizle
        return jsonPart.replace("\n", "").replace("\r", "").trim();
    }

    /**
     * Batch sonucunu JSON olarak döndürür
     */
    public static String toJson(BatchResult result) throws IOException {
        ObjectNode root = MAPPER.createObjectNode();
        root.put("totalFiles", result.getTotalFiles());
        root.put("successfulFiles", result.getSuccessfulFiles());
        root.put("failedFiles", result.getFailedFiles());
        
        ArrayNode files = root.putArray("files");
        for (FileAnalysis analysis : result.getAnalyses()) {
            ObjectNode fileNode = files.addObject();
            fileNode.put("fileName", analysis.getFileName());
            fileNode.put("success", analysis.isSuccess());
            
            if (analysis.isSuccess()) {
                fileNode.set("analysis", analysis.getAnalysis());
            } else {
                fileNode.put("error", analysis.getError());
            }
        }
        
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
    }

    // Inner classes
    public static class BatchResult {
        private final List<FileAnalysis> analyses;
        
        public BatchResult() {
            this.analyses = new ArrayList<>();
        }
        
        public BatchResult(List<FileAnalysis> analyses) {
            this.analyses = analyses;
        }
        
        public List<FileAnalysis> getAnalyses() {
            return analyses;
        }
        
        public int getTotalFiles() {
            return analyses.size();
        }
        
        public int getSuccessfulFiles() {
            return (int) analyses.stream().filter(FileAnalysis::isSuccess).count();
        }
        
        public int getFailedFiles() {
            return getTotalFiles() - getSuccessfulFiles();
        }
    }
    
    public static class FileAnalysis {
        private final String fileName;
        private final JsonNode analysis;
        private final String error;
        
        public FileAnalysis(String fileName, JsonNode analysis, String error) {
            this.fileName = fileName;
            this.analysis = analysis;
            this.error = error;
        }
        
        public String getFileName() {
            return fileName;
        }
        
        public JsonNode getAnalysis() {
            return analysis;
        }
        
        public String getError() {
            return error;
        }
        
        public boolean isSuccess() {
            return error == null;
        }
    }
}
