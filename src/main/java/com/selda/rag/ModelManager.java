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
        return "ÖNEMLİ: SADECE geçerli JSON ile yanıt ver. Açıklama yok, JSON öncesi veya sonrası metin yok.\n" +
                "CRITICAL: TÜM YANITLAR TÜRKÇE OLMALIDIR. Respond in TURKISH language only.\n\n" +
                "Gerekli JSON şeması - her alan STRING dizisi olmalı (obje değil):\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"gereksinim1\", \"gereksinim2\"],\n" +
                "  \"nonFunctionalRequirements\": [\"gereksinim1\", \"gereksinim2\"],\n" +
                "  \"missingInformation\": [\"eksik1\", \"eksik2\"],\n" +
                "  \"priorityHints\": [\"ipucu1\", \"ipucu2\"]\n" +
                "}\n\n" +
                "KRİTİK: Her dizi elemanı düz string olmalı, obje DEĞİL. Örnek: [\"madde1\", \"madde2\"], [{\"text\": \"madde1\"}] DEĞİL\n\n" +
                "Aşağıdaki gereksinim metnini analiz et ve SADECE JSON objesi ile yanıt ver ({ ile başlayıp } ile biten):\n\n" +
                "{content}";
    }
    
    private String getEnhancedPromptTemplate() {
        return "Sen uzman bir yazılım gereksinim analistisin.\n" +
                "CRITICAL: TÜM YANITLAR TÜRKÇE OLMALIDIR. Respond in TURKISH language only.\n\n" +
                "KRİTİK: SADECE geçerli JSON ile yanıt ver. Açıklama yok, markdown yok, kod bloğu yok, öncesi/sonrası metin yok.\n\n" +
                "Gerekli JSON şeması - diziler STRING içermeli, obje değil:\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"gereksinim 1\", \"gereksinim 2\"],\n" +
                "  \"nonFunctionalRequirements\": [\"gereksinim 1\", \"gereksinim 2\"],\n" +
                "  \"missingInformation\": [\"eksik madde 1\", \"eksik madde 2\"],\n" +
                "  \"priorityHints\": [\"ipucu 1\", \"ipucu 2\"]\n" +
                "}\n\n" +
                "ÖNEMLİ: Her dizi [\"metin1\", \"metin2\"] şeklinde string dizisi olmalı. [{\"text\": \"...\"}] şeklinde obje KULLANMA.\n\n" +
                "Kılavuz:\n" +
                "- Fonksiyonel gereksinimler: Sistemin ne yapması gerektiği (düz string olarak)\n" +
                "- Fonksiyonel olmayan gereksinimler: Performans, güvenlik, kullanılabilirlik (düz string olarak)\n" +
                "- Eksik bilgiler: Hangi ek detaylar gerekli (düz string olarak)\n" +
                "- Öncelik ipuçları: Önerilen uygulama önceliği (düz string olarak)\n\n" +
                "Analiz edilecek gereksinim metni:\n" +
                "{content}\n\n" +
                "Unutma: SADECE string dizileri içeren JSON objesi ile yanıt ver, başka hiçbir şey yazma. TÜRKÇE yanıt ver.";
    }
    
    private String getDetailedPromptTemplate() {
        return "Sen 20+ yıl deneyimli kıdemli bir yazılım mimarı ve gereksinim analistisin.\n" +
                "CRITICAL: TÜM YANITLAR TÜRKÇE OLMALIDIR. Respond in TURKISH language only.\n\n" +
                "KRİTİK: Yanıtın SADECE geçerli JSON olmalı. Açıklama yok, markdown formatı yok, kod bloğu yok, JSON objesi dışında metin yok.\n\n" +
                "Gerekli JSON şeması - tüm diziler SADECE STRING içermeli:\n" +
                "{\n" +
                "  \"functionalRequirements\": [\"gereksinim 1\", \"gereksinim 2\"],\n" +
                "  \"nonFunctionalRequirements\": [\"gereksinim 1\", \"gereksinim 2\"],\n" +
                "  \"missingInformation\": [\"eksik madde 1\", \"eksik madde 2\"],\n" +
                "  \"priorityHints\": [\"ipucu 1\", \"ipucu 2\"]\n" +
                "}\n\n" +
                "KRİTİK: String dizileri kullan [\"metin1\", \"metin2\"], obje dizileri DEĞİL [{\"text\": \"...\"}]. Her eleman düz string olmalı.\n\n" +
                "Analiz Kılavuzu:\n" +
                "- Fonksiyonel: Belirli sistem davranışları ve yetenekleri (string dizisi olarak)\n" +
                "- Fonksiyonel olmayan: Performans, ölçeklenebilirlik, güvenlik, sürdürülebilirlik (string dizisi olarak)\n" +
                "- Eksik: Gereksinimlerdeki boşluklar, belirsiz spesifikasyonlar (string dizisi olarak)\n" +
                "- Öncelik: Risk değerlendirmesi ve uygulama sırası (string dizisi olarak)\n\n" +
                "Teknik fizibilite, iş değeri ve uygulama karmaşıklığını göz önünde bulundur.\n\n" +
                "Gereksinim metni:\n" +
                "{content}\n\n" +
                "SADECE string dizileri içeren JSON objesi ile yanıt ver, { ile başlayıp } ile biten. TÜRKÇE yanıt ver.";
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
