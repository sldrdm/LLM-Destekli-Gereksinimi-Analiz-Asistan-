package com.selda.rag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class OllamaClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build();
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public static void main(String[] args) throws IOException {

        // Örnek iş gereksinimi metni
        String requirement = "Sistem, kullanıcı girişini doğrulamalıdır. Hatalı giriş yapıldığında kullanıcıya uygun bir hata mesajı gösterilmelidir.\n" +
                "Sisteme kayıt olan kullanıcılar e-posta ile doğrulanmalıdır.";

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

        // JSON parse etme
        try {
            JsonNode root = MAPPER.readTree(responseBody);
            System.out.println("Parsed JSON (pretty):");
            System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root));

            if (root.has("response")) {
                System.out.println("\nAssistant response (response):\n" + root.get("response").asText());
            } else if (root.has("choices")) {
                System.out.println("\nAssistant response (choices):\n" + root.get("choices").toString());
            } else {
                System.out.println("\nNo 'response' or 'choices' field found — inspect raw JSON above.");
            }

        } catch (Exception e) {
            System.out.println("Could not parse response as JSON automatically. Here's the raw body above.");
        }
    }
}
