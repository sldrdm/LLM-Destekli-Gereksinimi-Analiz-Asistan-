package com.selda.rag;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelConfig {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("displayName")
    private String displayName;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("temperature")
    private double temperature;
    
    @JsonProperty("maxTokens")
    private int maxTokens;
    
    @JsonProperty("timeout")
    private int timeoutSeconds;
    
    @JsonProperty("memoryUsage")
    private String memoryUsage;
    
    @JsonProperty("speed")
    private String speed;
    
    @JsonProperty("quality")
    private String quality;
    
    @JsonProperty("promptTemplate")
    private String promptTemplate;
    
    @JsonProperty("enabled")
    private boolean enabled;

    // Constructors
    public ModelConfig() {}

    public ModelConfig(String name, String displayName, String description, 
                      double temperature, int maxTokens, int timeoutSeconds,
                      String memoryUsage, String speed, String quality,
                      String promptTemplate, boolean enabled) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        this.timeoutSeconds = timeoutSeconds;
        this.memoryUsage = memoryUsage;
        this.speed = speed;
        this.quality = quality;
        this.promptTemplate = promptTemplate;
        this.enabled = enabled;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getMaxTokens() { return maxTokens; }
    public void setMaxTokens(int maxTokens) { this.maxTokens = maxTokens; }

    public int getTimeoutSeconds() { return timeoutSeconds; }
    public void setTimeoutSeconds(int timeoutSeconds) { this.timeoutSeconds = timeoutSeconds; }

    public String getMemoryUsage() { return memoryUsage; }
    public void setMemoryUsage(String memoryUsage) { this.memoryUsage = memoryUsage; }

    public String getSpeed() { return speed; }
    public void setSpeed(String speed) { this.speed = speed; }

    public String getQuality() { return quality; }
    public void setQuality(String quality) { this.quality = quality; }

    public String getPromptTemplate() { return promptTemplate; }
    public void setPromptTemplate(String promptTemplate) { this.promptTemplate = promptTemplate; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    public String toString() {
        return "ModelConfig{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", temperature=" + temperature +
                ", maxTokens=" + maxTokens +
                ", speed='" + speed + '\'' +
                ", quality='" + quality + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
