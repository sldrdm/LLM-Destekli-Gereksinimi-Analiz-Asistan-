package com.selda.rag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OllamaClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build();
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public static void main(String[] args) throws IOException {
        
        String requirement;
        
        // Kullanım talimatları
        if (args.length > 0 && (args[0].equals("-h") || args[0].equals("--help"))) {
            System.out.println("Kullanım:");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar [dosya_yolu] [--output çıktı.json] [--report html|pdf]");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar --batch [klasör_yolu] [çıktı.json] [--report html|pdf]");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar --files dosya1.pdf dosya2.docx [--output çıktı.json] [--report html|pdf]");
            System.out.println("");
            System.out.println("Örnekler:");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar requirements.pdf");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar document.docx --output sonuc.json --report html");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar --batch ./requirements/ analiz.json --report pdf");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar --files req1.pdf req2.docx --output sonuc.json --report html");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar  (varsayılan test metni)");
            System.out.println("");
            System.out.println("Desteklenen formatlar: PDF (.pdf), Word (.docx)");
            System.out.println("Çıktı: JSON formatında analiz sonuçları otomatik olarak dosyaya kaydedilir");
            System.out.println("Rapor: HTML veya PDF formatında profesyonel rapor oluşturur");
            System.exit(0);
        }
        
        // Batch işleme kontrolü
        if (args.length > 0 && args[0].equals("--batch")) {
            if (args.length < 2) {
                System.err.println("Hata: Batch modu için klasör yolu belirtmelisiniz.");
                System.err.println("Örnek: java -jar ba-llm-1.0-SNAPSHOT.jar --batch ./requirements/");
                System.exit(1);
            }
            
            String directoryPath = args[1];
            String outputFile = "batch-analysis-result.json";
            String reportType = null;
            
            // Parametreleri parse et
            for (int i = 2; i < args.length; i++) {
                if (args[i].equals("--output") && i + 1 < args.length) {
                    outputFile = args[i + 1];
                    i++; // Bir sonraki argümanı atla
                } else if (args[i].equals("--report") && i + 1 < args.length) {
                    reportType = args[i + 1];
                    i++; // Bir sonraki argümanı atla
                } else if (!args[i].startsWith("--")) {
                    outputFile = args[i];
                }
            }
            
            System.out.println("Batch analizi başlatılıyor...");
            System.out.println("Klasör: " + directoryPath);
            System.out.println("Çıktı dosyası: " + outputFile);
            if (reportType != null) {
                System.out.println("Rapor tipi: " + reportType);
            }
            
            try {
                BatchAnalyzer.BatchResult result = BatchAnalyzer.analyzeDirectory(directoryPath);
                String jsonOutput = BatchAnalyzer.toJson(result);
                
                // Dosyaya kaydet
                java.io.FileWriter writer = new java.io.FileWriter(outputFile);
                writer.write(jsonOutput);
                writer.close();
                
                System.out.println("\n=== BATCH ANALİZ SONUCU ===");
                System.out.println("Toplam dosya: " + result.getTotalFiles());
                System.out.println("Başarılı: " + result.getSuccessfulFiles());
                System.out.println("Başarısız: " + result.getFailedFiles());
                System.out.println("Sonuç kaydedildi: " + outputFile);
                
                // Rapor oluştur
                if (reportType != null) {
                    generateBatchReport(result, outputFile, directoryPath, reportType);
                }
                
                System.out.println("\nDetaylı JSON çıktısı:");
                System.out.println(jsonOutput);
                
            } catch (Exception e) {
                System.err.println("Batch analiz hatası: " + e.getMessage());
                System.exit(1);
            }
            
            return;
        }
        
        // Çoklu dosya işleme kontrolü
        if (args.length > 0 && args[0].equals("--files")) {
            if (args.length < 2) {
                System.err.println("Hata: --files modu için en az bir dosya belirtmelisiniz.");
                System.err.println("Örnek: java -jar ba-llm-1.0-SNAPSHOT.jar --files dosya1.pdf dosya2.docx");
                System.exit(1);
            }
            
            List<String> filePaths = new ArrayList<>();
            String outputFile = null;
            String reportType = null;
            
            // Dosya yollarını ve çıktı dosyasını ayır
            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("--output") && i + 1 < args.length) {
                    outputFile = args[i + 1];
                    i++; // Bir sonraki argümanı atla
                } else if (args[i].equals("--report") && i + 1 < args.length) {
                    reportType = args[i + 1];
                    i++; // Bir sonraki argümanı atla
                } else {
                    filePaths.add(args[i]);
                }
            }
            
            if (outputFile == null) {
                outputFile = "files-analysis-result.json";
            }
            
            System.out.println("Çoklu dosya analizi başlatılıyor...");
            System.out.println("Dosyalar: " + String.join(", ", filePaths));
            System.out.println("Çıktı dosyası: " + outputFile);
            if (reportType != null) {
                System.out.println("Rapor tipi: " + reportType);
            }
            
            try {
                BatchAnalyzer.BatchResult result = BatchAnalyzer.analyzeFiles(filePaths);
                String jsonOutput = BatchAnalyzer.toJson(result);
                
                // Dosyaya kaydet
                java.io.FileWriter writer = new java.io.FileWriter(outputFile);
                writer.write(jsonOutput);
                writer.close();
                
                System.out.println("\n=== ÇOKLU DOSYA ANALİZ SONUCU ===");
                System.out.println("Toplam dosya: " + result.getTotalFiles());
                System.out.println("Başarılı: " + result.getSuccessfulFiles());
                System.out.println("Başarısız: " + result.getFailedFiles());
                System.out.println("Sonuç kaydedildi: " + outputFile);
                
                // Rapor oluştur
                if (reportType != null) {
                    generateBatchReport(result, outputFile, String.join(", ", filePaths), reportType);
                }
                
                System.out.println("\nDetaylı JSON çıktısı:");
                System.out.println(jsonOutput);
                
            } catch (Exception e) {
                System.err.println("Çoklu dosya analiz hatası: " + e.getMessage());
                System.exit(1);
            }
            
            return;
        }
        
        // Komut satırı argümanı kontrolü
        String outputFile = null;
        String reportType = null;
        if (args.length > 0) {
            String filePath = args[0];
            
            // Parametreleri parse et
            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("--output") && i + 1 < args.length) {
                    outputFile = args[i + 1];
                    i++; // Bir sonraki argümanı atla
                } else if (args[i].equals("--report") && i + 1 < args.length) {
                    reportType = args[i + 1];
                    i++; // Bir sonraki argümanı atla
                }
            }
            
            if (outputFile == null) {
                // Dosya adından çıktı dosyası oluştur
                String fileName = new java.io.File(filePath).getName();
                outputFile = fileName.substring(0, fileName.lastIndexOf('.')) + "-analysis-result.json";
            }
            
            System.out.println("Dosya okunuyor: " + filePath);
            System.out.println("Çıktı dosyası: " + outputFile);
            if (reportType != null) {
                System.out.println("Rapor tipi: " + reportType);
            }
            
            if (!DocumentReader.fileExists(filePath)) {
                System.err.println("Hata: Dosya bulunamadı: " + filePath);
                System.exit(1);
            }
            
            try {
                requirement = DocumentReader.readDocument(filePath);
                System.out.println("Dosya başarıyla okundu. Metin uzunluğu: " + requirement.length() + " karakter");
            } catch (Exception e) {
                System.err.println("Dosya okuma hatası: " + e.getMessage());
                System.exit(1);
                return;
            }
        } else {
            // Varsayılan test metni
            requirement = "Sistem, kullanıcı girişini doğrulamalıdır. Hatalı giriş yapıldığında kullanıcıya uygun bir hata mesajı gösterilmelidir.\n" +
                    "Sisteme kayıt olan kullanıcılar e-posta ile doğrulanmalıdır.";
            outputFile = "default-analysis-result.json";
            System.out.println("Dosya yolu belirtilmedi, varsayılan test metni kullanılıyor.");
        }

        // Prompt: ModelManager'dan template kullan
        String prompt = ModelManager.getInstance().buildPrompt(requirement);

        // Request body: ModelManager'dan parametreler al
        Map<String, Object> params = ModelManager.getInstance().getModelParameters();
        
        ObjectNode requestNode = MAPPER.createObjectNode();
        requestNode.put("model", (String) params.get("model"));
        requestNode.put("prompt", prompt);
        requestNode.put("stream", false);
        
        ObjectNode optionsNode = MAPPER.createObjectNode();
        optionsNode.put("temperature", (Double) params.get("temperature"));
        optionsNode.put("num_predict", (Integer) params.get("max_tokens"));
        requestNode.set("options", optionsNode);
        
        String requestBody = requestNode.toString();

        RequestBody body = RequestBody.create(
                requestBody, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(OLLAMA_URL)
                .post(body)
                .build();

        System.out.println("Sending request to Ollama...");
        Response response = CLIENT.newCall(request).execute();

        System.out.println("HTTP status: " + response.code());
        String responseBody = response.body().string();
        System.out.println("Raw response:\n" + responseBody + "\n\n---\n");

        // JSON parse etme ve dosyaya kaydetme
        try {
            JsonNode root = MAPPER.readTree(responseBody);
            System.out.println("Parsed JSON (pretty):");
            System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root));

            if (root.has("response")) {
                String responseText = root.get("response").asText();
                System.out.println("\nAssistant response (response):\n" + responseText);
                
                // JSON parse etmeye çalış
                try {
                    String jsonPart = extractJsonFromResponse(responseText);
                    JsonNode analysisResult = MAPPER.readTree(jsonPart);
                    
                    // Analiz sonucunu dosyaya kaydet
                    java.io.FileWriter writer = new java.io.FileWriter(outputFile);
                    writer.write(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(analysisResult));
                    writer.close();
                    
                    System.out.println("\nParsed Analysis Result:");
                    System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(analysisResult));
                    System.out.println("\n✅ Analiz sonucu kaydedildi: " + outputFile);
                    
                    // Rapor oluştur
                    if (reportType != null) {
                        String sourceFile = args.length > 0 ? args[0] : "varsayılan test metni";
                        generateReport(analysisResult, outputFile, sourceFile, reportType);
                    }
                    
                } catch (Exception e) {
                    System.out.println("Analysis result could not be parsed as JSON.");
                }
            } else if (root.has("choices")) {
                System.out.println("\nAssistant response (choices):\n" + root.get("choices").toString());
            } else {
                System.out.println("\nNo 'response' or 'choices' field found — inspect raw JSON above.");
            }

        } catch (Exception e) {
            System.out.println("Could not parse response as JSON automatically. Here's the raw body above.");
        }
    }

    /**
     * Yanıttan JSON kısmını çıkarır - iyileştirilmiş versiyon
     */
    private static String extractJsonFromResponse(String responseText) {
        if (responseText == null || responseText.trim().isEmpty()) {
            throw new RuntimeException("Yanıt metni boş");
        }
        
        // Trim yap
        responseText = responseText.trim();
        
        // Eğer zaten tam JSON formatındaysa (başında { ve sonunda })
        if (responseText.startsWith("{") && responseText.endsWith("}")) {
            // Basit doğrulama: parantez sayısını kontrol et
            long openBraces = responseText.chars().filter(ch -> ch == '{').count();
            long closeBraces = responseText.chars().filter(ch -> ch == '}').count();
            
            if (openBraces == closeBraces) {
                return responseText;
            }
        }
        
        // İlk { karakterini bul
        int startIndex = responseText.indexOf('{');
        if (startIndex == -1) {
            throw new RuntimeException("JSON bulunamadı - yanıt metninde '{' karakteri yok. Yanıt: " + 
                (responseText.length() > 200 ? responseText.substring(0, 200) + "..." : responseText));
        }
        
        // Nested JSON için doğru kapanış parantezini bul
        int braceCount = 0;
        int lastIndex = -1;
        
        for (int i = startIndex; i < responseText.length(); i++) {
            char c = responseText.charAt(i);
            if (c == '{') {
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    lastIndex = i;
                    break;
                }
            }
        }
        
        // Eğer kapanış bulunamadıysa
        if (lastIndex == -1 || lastIndex <= startIndex) {
            // Son } karakterini kullan
            lastIndex = responseText.lastIndexOf('}');
            if (lastIndex <= startIndex) {
                // JSON tamamlanmamış olabilir (kesilmiş), tamamlamaya çalış
                String jsonPart = responseText.substring(startIndex);
                // Temizle
                jsonPart = jsonPart.replace("\n", " ").replace("\r", " ").replaceAll("\\s+", " ").trim();
                
                // Açık parantezleri say ve kapat
                int openBraces = (int) jsonPart.chars().filter(ch -> ch == '{').count();
                int closeBraces = (int) jsonPart.chars().filter(ch -> ch == '}').count();
                int openBrackets = (int) jsonPart.chars().filter(ch -> ch == '[').count();
                int closeBrackets = (int) jsonPart.chars().filter(ch -> ch == ']').count();
                
                // Eksik kapanış parantezlerini ekle
                while (closeBraces < openBraces) {
                    jsonPart += "}";
                    closeBraces++;
                }
                while (closeBrackets < openBrackets) {
                    jsonPart += "]";
                    closeBrackets++;
                }
                
                // Son } eksikse ekle
                if (!jsonPart.endsWith("}")) {
                    jsonPart += "}";
                }
                
                return jsonPart;
            }
        }
        
        // JSON kısmını çıkar
        String jsonPart = responseText.substring(startIndex, lastIndex + 1);
        
        // Fazla whitespace'leri temizle (ama JSON içindeki boşlukları koru)
        // Sadece satır sonlarını space'e çevir
        jsonPart = jsonPart.replace("\n", " ").replace("\r", " ");
        // Birden fazla ardışık boşluğu tek boşluğa indir (ama JSON string değerlerini koru)
        jsonPart = jsonPart.replaceAll("(\\s)(?=\\s)", ""); // Sadece ardışık whitespace'leri temizle
        
        return jsonPart.trim();
    }

    /**
     * Analiz sonucunu normalize eder - object array formatını string array formatına çevirir
     */
    private static JsonNode normalizeAnalysisResult(JsonNode result) {
        ObjectNode normalized = MAPPER.createObjectNode();
        
        // Her bir field için kontrol et
        String[] fields = {"functionalRequirements", "nonFunctionalRequirements", "missingInformation", "priorityHints"};
        
        for (String field : fields) {
            if (result.has(field) && result.get(field).isArray()) {
                com.fasterxml.jackson.databind.node.ArrayNode stringArray = MAPPER.createArrayNode();
                JsonNode arrayNode = result.get(field);
                
                for (JsonNode item : arrayNode) {
                    if (item.isTextual()) {
                        // Zaten string ise direkt ekle
                        stringArray.add(item.asText());
                    } else if (item.isObject()) {
                        // Object ise "text", "hint" veya "description" field'ını al
                        String textValue = null;
                        if (item.has("text")) {
                            textValue = item.get("text").asText();
                        } else if (item.has("hint")) {
                            textValue = item.get("hint").asText();
                        } else if (item.has("description")) {
                            textValue = item.get("description").asText();
                        } else {
                            // Eğer hiçbir field yoksa, ilk text field'ını dene
                            java.util.Iterator<java.util.Map.Entry<String, JsonNode>> fieldIterator = item.fields();
                            while (fieldIterator.hasNext()) {
                                java.util.Map.Entry<String, JsonNode> entry = fieldIterator.next();
                                if (entry.getValue().isTextual()) {
                                    textValue = entry.getValue().asText();
                                    break; // İlk text field'ını al ve çık
                                }
                            }
                        }
                        if (textValue != null && !textValue.isEmpty()) {
                            stringArray.add(textValue);
                        }
                    }
                }
                
                normalized.set(field, stringArray);
            } else if (result.has(field)) {
                // Field varsa ama array değilse, koru
                normalized.set(field, result.get(field));
            } else {
                // Field yoksa boş array ekle
                normalized.set(field, MAPPER.createArrayNode());
            }
        }
        
        return normalized;
    }
    
    /**
     * Rapor oluşturur
     */
    private static void generateReport(JsonNode analysisResult, String jsonFile, String sourceFile, String reportType) {
        try {
            String baseFileName = jsonFile.replace("-analysis-result.json", "");
            String reportFile;
            
            switch (reportType.toLowerCase()) {
                case "html":
                    reportFile = baseFileName + "-report.html";
                    ReportGenerator.generateHTMLReport(analysisResult, reportFile, sourceFile);
                    System.out.println("📄 HTML raporu oluşturuldu: " + reportFile);
                    break;
                case "pdf":
                    reportFile = baseFileName + "-report.pdf";
                    ReportGenerator.generatePDFReport(analysisResult, reportFile, sourceFile);
                    System.out.println("📄 PDF raporu oluşturuldu: " + reportFile);
                    break;
                default:
                    System.err.println("❌ Geçersiz rapor tipi: " + reportType + " (html veya pdf olmalı)");
            }
        } catch (Exception e) {
            System.err.println("❌ Rapor oluşturma hatası: " + e.getMessage());
        }
    }

    /**
     * Batch rapor oluşturur
     */
    private static void generateBatchReport(BatchAnalyzer.BatchResult batchResult, String jsonFile, String sourceInfo, String reportType) {
        try {
            String baseFileName = jsonFile.replace("-analysis-result.json", "");
            String reportFile;
            
            switch (reportType.toLowerCase()) {
                case "html":
                    reportFile = baseFileName + "-report.html";
                    ReportGenerator.generateBatchHTMLReport(batchResult, reportFile);
                    System.out.println("📄 Batch HTML raporu oluşturuldu: " + reportFile);
                    break;
                case "pdf":
                    reportFile = baseFileName + "-report.pdf";
                    ReportGenerator.generateBatchPDFReport(batchResult, reportFile);
                    System.out.println("📄 Batch PDF raporu oluşturuldu: " + reportFile);
                    break;
                default:
                    System.err.println("❌ Geçersiz rapor tipi: " + reportType + " (html veya pdf olmalı)");
            }
        } catch (Exception e) {
            System.err.println("❌ Batch rapor oluşturma hatası: " + e.getMessage());
        }
    }

    /**
     * Web arayüzü için metin analizi
     */
    public static JsonNode analyzeText(String prompt) throws IOException {
        return analyzeText(prompt, null);
    }

    /**
     * Belirli model ile metin analizi
     */
    public static JsonNode analyzeText(String prompt, String modelName) throws IOException {
        ModelManager modelManager = ModelManager.getInstance();
        ModelConfig modelConfig;
        
        if (modelName != null) {
            modelConfig = modelManager.getModel(modelName);
            if (modelConfig == null) {
                throw new IOException("Model bulunamadı: " + modelName);
            }
        } else {
            modelConfig = modelManager.getCurrentModel();
        }
        
        if (modelConfig == null) {
            throw new IOException("Aktif model bulunamadı");
        }

        Map<String, Object> params = modelManager.getModelParameters();
        if (modelName != null) {
            params.put("model", modelName);
        }

        ObjectNode requestNode = MAPPER.createObjectNode();
        requestNode.put("model", (String) params.get("model"));
        requestNode.put("prompt", prompt);
        requestNode.put("stream", false);
        
        ObjectNode optionsNode = MAPPER.createObjectNode();
        optionsNode.put("temperature", (Double) params.get("temperature"));
        optionsNode.put("num_predict", (Integer) params.get("max_tokens"));
        requestNode.set("options", optionsNode);
        
        String requestBody = requestNode.toString();

        RequestBody body = RequestBody.create(
                requestBody, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(OLLAMA_URL)
                .post(body)
                .build();

        Response response = CLIENT.newCall(request).execute();
        String responseBody = response.body().string();

        if (!response.isSuccessful()) {
            throw new IOException("Ollama API hatası: " + response.code() + " - " + responseBody);
        }

        JsonNode root = MAPPER.readTree(responseBody);
        JsonNode analysisResult = null;

        if (root.has("response")) {
            String responseText = root.get("response").asText();
            
            // Debug için response'u logla (ilk 500 karakter)
            String debugPreview = responseText.length() > 500 
                ? responseText.substring(0, 500) + "..." 
                : responseText;
            System.err.println("Ollama yanıtı (ilk 500 karakter): " + debugPreview);
            
            try {
                String jsonPart = extractJsonFromResponse(responseText);
                System.err.println("Çıkarılan JSON kısmı: " + 
                    (jsonPart.length() > 500 ? jsonPart.substring(0, 500) + "..." : jsonPart));
                
                analysisResult = MAPPER.readTree(jsonPart);
                
                // JSON'un beklenen yapıda olduğunu kontrol et
                if (!analysisResult.isObject()) {
                    throw new IOException("Parse edilen sonuç bir JSON objesi değil");
                }
                
                // Object array formatını string array formatına normalize et
                analysisResult = normalizeAnalysisResult(analysisResult);
                
            } catch (Exception e) {
                String errorMsg = "JSON parse hatası: " + e.getMessage() + 
                    "\nOllama yanıtı: " + (responseText.length() > 1000 ? responseText.substring(0, 1000) + "..." : responseText);
                System.err.println(errorMsg);
                e.printStackTrace();
                throw new IOException(errorMsg, e);
            }
        } else {
            throw new IOException("Geçersiz yanıt formatı - 'response' alanı bulunamadı. Yanıt: " + 
                (responseBody.length() > 500 ? responseBody.substring(0, 500) + "..." : responseBody));
        }

        return analysisResult;
    }

    /**
     * Model bilgilerini al
     */
    public static Map<String, Object> getModelInfo() {
        ModelManager modelManager = ModelManager.getInstance();
        Map<String, Object> info = new HashMap<>();
        
        info.put("currentModel", modelManager.getCurrentModel());
        info.put("availableModels", modelManager.getEnabledModels());
        info.put("modelStatus", modelManager.getModelStatus());
        info.put("parameters", modelManager.getModelParameters());
        
        return info;
    }

    /**
     * Model değiştir
     */
    public static boolean setModel(String modelName) {
        ModelManager modelManager = ModelManager.getInstance();
        ModelConfig config = modelManager.getModel(modelName);
        
        if (config != null && config.isEnabled()) {
            modelManager.setCurrentModel(modelName);
            return true;
        }
        return false;
    }
}
