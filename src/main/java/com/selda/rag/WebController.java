package com.selda.rag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class WebController {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String UPLOAD_DIR = "uploads/";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "BA-LLM Gereksinim Analizi");
        model.addAttribute("currentTime", LocalDateTime.now().format(DATE_FORMAT));
        
        // Model bilgilerini ekle
        Map<String, Object> modelInfo = OllamaClient.getModelInfo();
        model.addAttribute("currentModel", modelInfo.get("currentModel"));
        model.addAttribute("availableModels", modelInfo.get("availableModels"));
        model.addAttribute("modelStatus", modelInfo.get("modelStatus"));
        
        return "index";
    }

    @PostMapping("/analyze")
    @ResponseBody
    public Map<String, Object> analyzeFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "reportType", defaultValue = "none") String reportType,
            @RequestParam(value = "modelName", required = false) String modelName,
            HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Dosya kontrolü
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("error", "Dosya seçilmedi");
                return response;
            }

            // Dosya uzantısı kontrolü
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || (!originalFilename.toLowerCase().endsWith(".pdf") && 
                !originalFilename.toLowerCase().endsWith(".docx"))) {
                response.put("success", false);
                response.put("error", "Sadece PDF ve DOCX dosyaları desteklenir");
                return response;
            }

            // Upload dizinini oluştur
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Dosyayı kaydet
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // Dosyayı analiz et
            String content = DocumentReader.readDocument(filePath.toString());
            
            // Ollama ile analiz yap (belirtilen model ile)
            JsonNode analysisResult = performAnalysis(content, modelName);
            
            // Sonuçları hazırla
            response.put("success", true);
            response.put("fileName", originalFilename);
            response.put("fileSize", file.getSize());
            response.put("analysisResult", analysisResult);
            response.put("timestamp", LocalDateTime.now().format(DATE_FORMAT));
            
            // Rapor oluştur
            if (!reportType.equals("none")) {
                String reportFileName = generateReport(analysisResult, originalFilename, reportType, request);
                response.put("reportFile", reportFileName);
                response.put("reportType", reportType);
            }

            // Geçici dosyayı sil
            Files.deleteIfExists(filePath);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Analiz hatası: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("/analyze-text")
    @ResponseBody
    public Map<String, Object> analyzeText(
            @RequestParam("text") String text,
            @RequestParam(value = "reportType", defaultValue = "none") String reportType,
            @RequestParam(value = "modelName", required = false) String modelName,
            HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (text == null || text.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "Metin boş olamaz");
                return response;
            }

            // Ollama ile analiz yap (belirtilen model ile)
            JsonNode analysisResult = performAnalysis(text.trim(), modelName);
            
            // Sonuçları hazırla
            response.put("success", true);
            response.put("textLength", text.length());
            response.put("analysisResult", analysisResult);
            response.put("timestamp", LocalDateTime.now().format(DATE_FORMAT));
            
            // Rapor oluştur
            if (!reportType.equals("none")) {
                String reportFileName = generateReport(analysisResult, "metin-analizi", reportType, request);
                response.put("reportFile", reportFileName);
                response.put("reportType", reportType);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Analiz hatası: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/download/{fileName}")
    public void downloadReport(@PathVariable String fileName, HttpServletRequest request, 
                              javax.servlet.http.HttpServletResponse response) throws IOException {
        
        String uploadPath = request.getServletContext().getRealPath("/") + "reports/";
        Path filePath = Paths.get(uploadPath + fileName);
        
        if (Files.exists(filePath)) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        } else {
            response.sendError(404, "Dosya bulunamadı");
        }
    }

    private JsonNode performAnalysis(String content, String modelName) throws IOException {
        // ModelManager'dan prompt template kullan
        String prompt = ModelManager.getInstance().buildPrompt(content);
        
        // OllamaClient'dan analiz yap (belirtilen model ile)
        return OllamaClient.analyzeText(prompt, modelName);
    }

    private String generateReport(JsonNode analysisResult, String sourceName, String reportType, HttpServletRequest request) throws IOException {
        String reportsDir = request.getServletContext().getRealPath("/") + "reports/";
        Path reportsPath = Paths.get(reportsDir);
        
        if (!Files.exists(reportsPath)) {
            Files.createDirectories(reportsPath);
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String baseFileName = sourceName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp;
        
        if (reportType.equals("html")) {
            String reportFile = baseFileName + "-report.html";
            String reportPath = reportsDir + reportFile;
            ReportGenerator.generateHTMLReport(analysisResult, reportPath, sourceName);
            return reportFile;
        } else if (reportType.equals("pdf")) {
            String reportFile = baseFileName + "-report.pdf";
            String reportPath = reportsDir + reportFile;
            ReportGenerator.generatePDFReport(analysisResult, reportPath, sourceName);
            return reportFile;
        }
        
        return null;
    }

    // Model yönetimi endpoint'leri
    @GetMapping("/api/models")
    @ResponseBody
    public Map<String, Object> getModels() {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> modelInfo = OllamaClient.getModelInfo();
            response.put("success", true);
            response.put("data", modelInfo);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/api/models/switch")
    @ResponseBody
    public Map<String, Object> switchModel(@RequestParam("modelName") String modelName) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = OllamaClient.setModel(modelName);
            if (success) {
                response.put("success", true);
                response.put("message", "Model başarıyla değiştirildi");
                response.put("currentModel", OllamaClient.getModelInfo().get("currentModel"));
            } else {
                response.put("success", false);
                response.put("error", "Model değiştirilemedi: " + modelName);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/api/models/parameters")
    @ResponseBody
    public Map<String, Object> updateModelParameters(@RequestBody Map<String, Object> parameters) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Bu endpoint gelecekte model parametrelerini güncellemek için kullanılabilir
            response.put("success", true);
            response.put("message", "Parametreler güncellendi");
            response.put("parameters", parameters);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
}
