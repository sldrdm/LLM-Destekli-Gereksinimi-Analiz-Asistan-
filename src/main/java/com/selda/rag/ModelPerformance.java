package com.selda.rag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModelPerformance {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);
    
    public static class PerformanceResult {
        private String modelName;
        private long durationMs;
        private JsonNode analysisResult;
        private boolean success;
        private String errorMessage;
        private LocalDateTime timestamp;
        
        public PerformanceResult(String modelName, long durationMs, JsonNode analysisResult, 
                               boolean success, String errorMessage) {
            this.modelName = modelName;
            this.durationMs = durationMs;
            this.analysisResult = analysisResult;
            this.success = success;
            this.errorMessage = errorMessage;
            this.timestamp = LocalDateTime.now();
        }
        
        // Getters
        public String getModelName() { return modelName; }
        public long getDurationMs() { return durationMs; }
        public JsonNode getAnalysisResult() { return analysisResult; }
        public boolean isSuccess() { return success; }
        public String getErrorMessage() { return errorMessage; }
        public LocalDateTime getTimestamp() { return timestamp; }
        
        public String getFormattedDuration() {
            if (durationMs < 1000) {
                return durationMs + "ms";
            } else {
                return String.format("%.1fs", durationMs / 1000.0);
            }
        }
    }
    
    public static class ComparisonResult {
        private List<PerformanceResult> results;
        private String fastestModel;
        private String bestQualityModel;
        private LocalDateTime timestamp;
        
        public ComparisonResult(List<PerformanceResult> results) {
            this.results = results;
            this.timestamp = LocalDateTime.now();
            calculateMetrics();
        }
        
        private void calculateMetrics() {
            // En hızlı model
            fastestModel = results.stream()
                .filter(PerformanceResult::isSuccess)
                .min(Comparator.comparing(PerformanceResult::getDurationMs))
                .map(PerformanceResult::getModelName)
                .orElse("Bilinmiyor");
            
            // En kaliteli model (şimdilik en uzun analizi yapan)
            bestQualityModel = results.stream()
                .filter(PerformanceResult::isSuccess)
                .filter(r -> r.getAnalysisResult() != null)
                .max((r1, r2) -> {
                    int count1 = countAnalysisItems(r1.getAnalysisResult());
                    int count2 = countAnalysisItems(r2.getAnalysisResult());
                    return Integer.compare(count1, count2);
                })
                .map(PerformanceResult::getModelName)
                .orElse("Bilinmiyor");
        }
        
        private int countAnalysisItems(JsonNode result) {
            int count = 0;
            if (result.has("functionalRequirements")) {
                count += result.get("functionalRequirements").size();
            }
            if (result.has("nonFunctionalRequirements")) {
                count += result.get("nonFunctionalRequirements").size();
            }
            if (result.has("missingInformation")) {
                count += result.get("missingInformation").size();
            }
            if (result.has("priorityHints")) {
                count += result.get("priorityHints").size();
            }
            return count;
        }
        
        // Getters
        public List<PerformanceResult> getResults() { return results; }
        public String getFastestModel() { return fastestModel; }
        public String getBestQualityModel() { return bestQualityModel; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    
    /**
     * Birden fazla model ile aynı metni analiz et ve performans karşılaştır
     */
    public static ComparisonResult compareModels(String content, List<String> modelNames) {
        List<CompletableFuture<PerformanceResult>> futures = new ArrayList<>();
        
        for (String modelName : modelNames) {
            CompletableFuture<PerformanceResult> future = CompletableFuture.supplyAsync(() -> {
                return analyzeWithModel(content, modelName);
            }, executor);
            futures.add(future);
        }
        
        List<PerformanceResult> results = new ArrayList<>();
        for (CompletableFuture<PerformanceResult> future : futures) {
            try {
                results.add(future.get());
            } catch (Exception e) {
                results.add(new PerformanceResult("Unknown", 0, null, false, e.getMessage()));
            }
        }
        
        return new ComparisonResult(results);
    }
    
    /**
     * Belirli bir model ile analiz yap ve performans ölç
     */
    private static PerformanceResult analyzeWithModel(String content, String modelName) {
        long startTime = System.currentTimeMillis();
        
        try {
            ModelManager modelManager = ModelManager.getInstance();
            ModelConfig config = modelManager.getModel(modelName);
            
            if (config == null) {
                return new PerformanceResult(modelName, 0, null, false, "Model bulunamadı: " + modelName);
            }
            
            String prompt = modelManager.buildPrompt(content);
            JsonNode result = OllamaClient.analyzeText(prompt, modelName);
            
            long duration = System.currentTimeMillis() - startTime;
            return new PerformanceResult(modelName, duration, result, true, null);
            
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            return new PerformanceResult(modelName, duration, null, false, e.getMessage());
        }
    }
    
    /**
     * Mevcut aktif modeller ile performans testi yap
     */
    public static ComparisonResult testCurrentModels(String testContent) {
        ModelManager modelManager = ModelManager.getInstance();
        List<ModelConfig> enabledModels = modelManager.getEnabledModels();
        
        List<String> modelNames = new ArrayList<>();
        for (ModelConfig config : enabledModels) {
            if (!config.getName().equals("custom:user")) {
                modelNames.add(config.getName());
            }
        }
        
        if (modelNames.isEmpty()) {
            throw new RuntimeException("Test edilecek aktif model bulunamadı");
        }
        
        return compareModels(testContent, modelNames);
    }
    
    /**
     * Performans sonuçlarını JSON formatında döndür
     */
    public static String toJson(ComparisonResult comparison) {
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("timestamp", comparison.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            result.put("fastestModel", comparison.getFastestModel());
            result.put("bestQualityModel", comparison.getBestQualityModel());
            
            List<Map<String, Object>> modelResults = new ArrayList<>();
            for (PerformanceResult perf : comparison.getResults()) {
                Map<String, Object> modelResult = new HashMap<>();
                modelResult.put("modelName", perf.getModelName());
                modelResult.put("durationMs", perf.getDurationMs());
                modelResult.put("formattedDuration", perf.getFormattedDuration());
                modelResult.put("success", perf.isSuccess());
                modelResult.put("errorMessage", perf.getErrorMessage());
                
                if (perf.isSuccess() && perf.getAnalysisResult() != null) {
                    modelResult.put("analysisResult", perf.getAnalysisResult());
                    
                    // Analiz kalitesi metrikleri
                    JsonNode analysis = perf.getAnalysisResult();
                    Map<String, Integer> qualityMetrics = new HashMap<>();
                    qualityMetrics.put("functionalRequirements", analysis.has("functionalRequirements") ? analysis.get("functionalRequirements").size() : 0);
                    qualityMetrics.put("nonFunctionalRequirements", analysis.has("nonFunctionalRequirements") ? analysis.get("nonFunctionalRequirements").size() : 0);
                    qualityMetrics.put("missingInformation", analysis.has("missingInformation") ? analysis.get("missingInformation").size() : 0);
                    qualityMetrics.put("priorityHints", analysis.has("priorityHints") ? analysis.get("priorityHints").size() : 0);
                    modelResult.put("qualityMetrics", qualityMetrics);
                }
                
                modelResults.add(modelResult);
            }
            
            result.put("modelResults", modelResults);
            
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (Exception e) {
            throw new RuntimeException("JSON dönüştürme hatası: " + e.getMessage());
        }
    }
    
    /**
     * Executor'ı kapat
     */
    public static void shutdown() {
        executor.shutdown();
    }
}
