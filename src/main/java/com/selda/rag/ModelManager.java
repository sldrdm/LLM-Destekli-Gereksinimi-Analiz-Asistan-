package com.selda.rag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ModelManager {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String CONFIG_FILE = "model-configs.json";
    private static ModelManager instance;
    private Map<String, ModelConfig> models;
    private String currentModel;
    
    private ModelManager() {
        loadModels();
        setDefaultModel();
    }
    
    public static synchronized ModelManager getInstance() {
        if (instance == null) {
            instance = new ModelManager();
        }
        return instance;
    }
    
    private void loadModels() {
        models = new ConcurrentHashMap<>();
        
        // Varsayılan modeller
        addDefaultModels();
        
        // Dosyadan yükle (varsa)
        try {
            if (Files.exists(Paths.get(CONFIG_FILE))) {
                loadFromFile();
            }
        } catch (IOException e) {
            System.err.println("Model konfigürasyonu yüklenirken hata: " + e.getMessage());
        }
    }
    
    private void addDefaultModels() {
        // Hızlı Model (Küçük, Hızlı)
        ModelConfig fastModel = new ModelConfig(
            "llama3.2:1b",
            "Llama 3.2 1B (Hızlı)",
            "En hızlı model, temel analizler için ideal",
            0.7,
            512,
            30,
            "~1GB",
            "Çok Hızlı",
            "İyi",
            getDefaultPromptTemplate(),
            true
        );
        models.put(fastModel.getName(), fastModel);
        
        // Dengeli Model (Orta boyut, dengeli)
        ModelConfig balancedModel = new ModelConfig(
            "llama3:latest",
            "Llama 3 (Dengeli)",
            "En iyi dengeyi sunan varsayılan model",
            0.7,
            1024,
            60,
            "~4GB",
            "Hızlı",
            "Çok İyi",
            getEnhancedPromptTemplate(),
            true
        );
        models.put(balancedModel.getName(), balancedModel);
        
        // Kaliteli Model (Büyük, Yavaş ama kaliteli)
        ModelConfig qualityModel = new ModelConfig(
            "llama3.2:3b",
            "Llama 3.2 3B (Kaliteli)",
            "En kaliteli analizler için büyük model",
            0.5,
            2048,
            120,
            "~6GB",
            "Orta",
            "Mükemmel",
            getDetailedPromptTemplate(),
            true
        );
        models.put(qualityModel.getName(), qualityModel);
        
        // Özel Model (Kullanıcı tanımlı)
        ModelConfig customModel = new ModelConfig(
            "custom:user",
            "Özel Model",
            "Kullanıcı tanımlı model ve parametreler",
            0.8,
            1024,
            90,
            "Değişken",
            "Değişken",
            "Değişken",
            getDefaultPromptTemplate(),
            false
        );
        models.put(customModel.getName(), customModel);
    }
    
    private String getDefaultPromptTemplate() {
        return "IMPORTANT: You MUST respond with ONLY valid JSON. No explanations, no additional text before or after the JSON.\n\n" +
                "Required JSON schema - each field must be an array of STRINGS (not objects):\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"string1\", \"string2\"],\n" +
                "  \"nonFunctionalRequirements\": [\"string1\", \"string2\"],\n" +
                "  \"missingInformation\": [\"string1\", \"string2\"],\n" +
                "  \"priorityHints\": [\"string1\", \"string2\"]\n" +
                "}\n\n" +
                "CRITICAL: Each array element must be a plain string, NOT an object. Example: [\"item1\", \"item2\"], NOT [{\"text\": \"item1\"}]\n\n" +
                "Analyze the following requirement text and respond with ONLY the JSON object (starting with { and ending with }):\n\n" +
                "{content}";
    }
    
    private String getEnhancedPromptTemplate() {
        return "You are an expert software requirements analyst.\n\n" +
                "CRITICAL: Respond with ONLY valid JSON. No explanations, no markdown, no code blocks, no text before or after.\n\n" +
                "Required JSON schema - arrays must contain STRINGS, not objects:\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"requirement 1\", \"requirement 2\"],\n" +
                "  \"nonFunctionalRequirements\": [\"requirement 1\", \"requirement 2\"],\n" +
                "  \"missingInformation\": [\"missing item 1\", \"missing item 2\"],\n" +
                "  \"priorityHints\": [\"hint 1\", \"hint 2\"]\n" +
                "}\n\n" +
                "IMPORTANT: Each array must be an array of strings like [\"text1\", \"text2\"]. Do NOT use objects like [{\"text\": \"...\"}].\n\n" +
                "Guidelines:\n" +
                "- Functional requirements: What the system should do (as plain strings)\n" +
                "- Non-functional requirements: Performance, security, usability (as plain strings)\n" +
                "- Missing information: What additional details are needed (as plain strings)\n" +
                "- Priority hints: Suggested implementation priority (as plain strings)\n\n" +
                "Requirement text to analyze:\n" +
                "{content}\n\n" +
                "Remember: Respond with ONLY the JSON object with string arrays, nothing else.";
    }
    
    private String getDetailedPromptTemplate() {
        return "You are a senior software architect and requirements analyst with 20+ years experience.\n\n" +
                "CRITICAL: Your response must be ONLY valid JSON. No explanations, no markdown formatting, no code blocks, no text outside the JSON object.\n\n" +
                "Required JSON schema - all arrays must contain STRINGS only:\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"requirement 1\", \"requirement 2\"],\n" +
                "  \"nonFunctionalRequirements\": [\"requirement 1\", \"requirement 2\"],\n" +
                "  \"missingInformation\": [\"missing item 1\", \"missing item 2\"],\n" +
                "  \"priorityHints\": [\"hint 1\", \"hint 2\"]\n" +
                "}\n\n" +
                "CRITICAL: Use string arrays [\"text1\", \"text2\"], NOT object arrays [{\"text\": \"...\"}]. Each element must be a plain string.\n\n" +
                "Analysis Guidelines:\n" +
                "- Functional: Specific system behaviors and capabilities (as string array)\n" +
                "- Non-functional: Performance, scalability, security, maintainability (as string array)\n" +
                "- Missing: Gaps in requirements, unclear specifications (as string array)\n" +
                "- Priority: Risk assessment and implementation order (as string array)\n\n" +
                "Consider technical feasibility, business value, and implementation complexity.\n\n" +
                "Requirement text:\n" +
                "{content}\n\n" +
                "Respond with ONLY the JSON object with string arrays, starting with { and ending with }.";
    }
    
    private void loadFromFile() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(CONFIG_FILE)));
        JsonNode root = MAPPER.readTree(content);
        
        if (root.has("models")) {
            JsonNode modelsNode = root.get("models");
            for (JsonNode modelNode : modelsNode) {
                ModelConfig config = MAPPER.treeToValue(modelNode, ModelConfig.class);
                models.put(config.getName(), config);
            }
        }
        
        if (root.has("currentModel")) {
            currentModel = root.get("currentModel").asText();
        }
    }
    
    public void saveToFile() throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("models", new ArrayList<>(models.values()));
        data.put("currentModel", currentModel);
        data.put("lastUpdated", System.currentTimeMillis());
        
        String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        Files.write(Paths.get(CONFIG_FILE), json.getBytes());
    }
    
    private void setDefaultModel() {
        // Önce aktif olan bir model bul
        for (ModelConfig config : models.values()) {
            if (config.isEnabled() && !config.getName().equals("custom:user")) {
                currentModel = config.getName();
                return;
            }
        }
        // Hiç aktif model yoksa varsayılan
        currentModel = "llama3:latest";
    }
    
    // Public methods
    public List<ModelConfig> getAvailableModels() {
        return new ArrayList<>(models.values());
    }
    
    public List<ModelConfig> getEnabledModels() {
        List<ModelConfig> enabled = new ArrayList<>();
        for (ModelConfig config : models.values()) {
            if (config.isEnabled()) {
                enabled.add(config);
            }
        }
        return enabled;
    }
    
    public ModelConfig getCurrentModel() {
        return models.get(currentModel);
    }
    
    public ModelConfig getModel(String name) {
        return models.get(name);
    }
    
    public void setCurrentModel(String name) {
        if (models.containsKey(name)) {
            currentModel = name;
            try {
                saveToFile();
            } catch (IOException e) {
                System.err.println("Model değişikliği kaydedilemedi: " + e.getMessage());
            }
        }
    }
    
    public void addModel(ModelConfig config) {
        models.put(config.getName(), config);
        try {
            saveToFile();
        } catch (IOException e) {
            System.err.println("Model eklenemedi: " + e.getMessage());
        }
    }
    
    public void updateModel(ModelConfig config) {
        if (models.containsKey(config.getName())) {
            models.put(config.getName(), config);
            try {
                saveToFile();
            } catch (IOException e) {
                System.err.println("Model güncellenemedi: " + e.getMessage());
            }
        }
    }
    
    public void removeModel(String name) {
        if (!name.equals("llama3:latest")) { // Varsayılan modeli silme
            models.remove(name);
            if (currentModel.equals(name)) {
                setDefaultModel();
            }
            try {
                saveToFile();
            } catch (IOException e) {
                System.err.println("Model silinemedi: " + e.getMessage());
            }
        }
    }
    
    public String buildPrompt(String content) {
        ModelConfig current = getCurrentModel();
        if (current != null && current.getPromptTemplate() != null) {
            return current.getPromptTemplate().replace("{content}", content);
        }
        return getDefaultPromptTemplate().replace("{content}", content);
    }
    
    public Map<String, Object> getModelParameters() {
        ModelConfig current = getCurrentModel();
        Map<String, Object> params = new HashMap<>();
        
        if (current != null) {
            params.put("model", current.getName());
            params.put("temperature", current.getTemperature());
            params.put("max_tokens", current.getMaxTokens());
        } else {
            params.put("model", "llama3:latest");
            params.put("temperature", 0.7);
            params.put("max_tokens", 1024);
        }
        
        return params;
    }
    
    public int getTimeoutSeconds() {
        ModelConfig current = getCurrentModel();
        return current != null ? current.getTimeoutSeconds() : 60;
    }
    
    public String getModelStatus() {
        ModelConfig current = getCurrentModel();
        if (current != null) {
            return String.format("%s - %s (%s, %s)", 
                current.getDisplayName(), 
                current.getSpeed(), 
                current.getMemoryUsage(),
                current.getQuality());
        }
        return "Model bilgisi bulunamadı";
    }
}
