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
        return "Respond ONLY with valid JSON matching this schema:\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"string\"],\n" +
                "  \"nonFunctionalRequirements\": [\"string\"],\n" +
                "  \"missingInformation\": [\"string\"],\n" +
                "  \"priorityHints\": [\"string\"]\n" +
                "}\n" +
                "Analyze the following requirement text and provide structured analysis:\n" +
                "{content}";
    }
    
    private String getEnhancedPromptTemplate() {
        return "You are an expert software requirements analyst. Respond ONLY with valid JSON matching this schema:\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"string\"],\n" +
                "  \"nonFunctionalRequirements\": [\"string\"],\n" +
                "  \"missingInformation\": [\"string\"],\n" +
                "  \"priorityHints\": [\"string\"]\n" +
                "}\n" +
                "Guidelines:\n" +
                "- Functional requirements: What the system should do\n" +
                "- Non-functional requirements: Performance, security, usability\n" +
                "- Missing information: What additional details are needed\n" +
                "- Priority hints: Suggested implementation priority\n" +
                "Requirement text to analyze:\n" +
                "{content}";
    }
    
    private String getDetailedPromptTemplate() {
        return "You are a senior software architect and requirements analyst with 20+ years experience. " +
                "Provide a comprehensive analysis in valid JSON format matching this schema:\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"string\"],\n" +
                "  \"nonFunctionalRequirements\": [\"string\"],\n" +
                "  \"missingInformation\": [\"string\"],\n" +
                "  \"priorityHints\": [\"string\"]\n" +
                "}\n" +
                "Analysis Guidelines:\n" +
                "- Functional: Specific system behaviors and capabilities\n" +
                "- Non-functional: Performance, scalability, security, maintainability\n" +
                "- Missing: Gaps in requirements, unclear specifications\n" +
                "- Priority: Risk assessment and implementation order\n" +
                "Consider technical feasibility, business value, and implementation complexity.\n" +
                "Requirement text:\n" +
                "{content}";
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
