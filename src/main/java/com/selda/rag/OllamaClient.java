package com.selda.rag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar [dosya_yolu] [--output çıktı.json]");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar --batch [klasör_yolu] [çıktı.json]");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar --files dosya1.pdf dosya2.docx [--output çıktı.json]");
            System.out.println("");
            System.out.println("Örnekler:");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar requirements.pdf");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar document.docx --output sonuc.json");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar --batch ./requirements/ analiz.json");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar --files req1.pdf req2.docx --output sonuc.json");
            System.out.println("  java -jar ba-llm-1.0-SNAPSHOT.jar  (varsayılan test metni)");
            System.out.println("");
            System.out.println("Desteklenen formatlar: PDF (.pdf), Word (.docx)");
            System.out.println("Çıktı: JSON formatında analiz sonuçları otomatik olarak dosyaya kaydedilir");
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
            String outputFile = args.length > 2 ? args[2] : "batch-analysis-result.json";
            
            System.out.println("Batch analizi başlatılıyor...");
            System.out.println("Klasör: " + directoryPath);
            System.out.println("Çıktı dosyası: " + outputFile);
            
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
            
            // Dosya yollarını ve çıktı dosyasını ayır
            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("--output") && i + 1 < args.length) {
                    outputFile = args[i + 1];
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
        if (args.length > 0) {
            String filePath = args[0];
            
            // Çıktı dosyası kontrolü
            if (args.length > 1 && args[1].equals("--output") && args.length > 2) {
                outputFile = args[2];
            } else {
                // Dosya adından çıktı dosyası oluştur
                String fileName = new java.io.File(filePath).getName();
                outputFile = fileName.substring(0, fileName.lastIndexOf('.')) + "-analysis-result.json";
            }
            
            System.out.println("Dosya okunuyor: " + filePath);
            System.out.println("Çıktı dosyası: " + outputFile);
            
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

        // Prompt: JSON formatında yanıt iste
        String prompt = "Respond ONLY with valid JSON matching this schema:\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"string\"],\n" +
                "  \"nonFunctionalRequirements\": [\"string\"],\n" +
                "  \"missingInformation\": [\"string\"],\n" +
                "  \"priorityHints\": [\"string\"]\n" +
                "}\n" +
                "ORIGINAL requirement:\n" +
                requirement;

        // Request body
        String requestBody = MAPPER.createObjectNode()
                .put("model",  "llama3:latest")
                .put("prompt",  prompt)
                .put("stream",  false)
                .toString();

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), requestBody);

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
}
