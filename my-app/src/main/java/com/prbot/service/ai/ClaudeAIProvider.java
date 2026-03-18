package com.prbot.service.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClaudeAIProvider implements AIProvider {
    @Value("${claude.api.url:}")
    private String apiUrl;

    @Value("${claude.api.key:}")
    private String apiKey;
    private final RestTemplate restTemplate = createRestTemplate();
    @Override
    public String getName() {
        return "ClaudeAI";
    }

    @Override
    public String analyzeCode(String prTitle, String prDiff, int prNumber, String repository) {
        // Implement the logic to call ClaudeAI API and return the analysis result
        try{
            String prompt = buildReviewPrompt(prTitle, prDiff, prNumber, repository);
            // Call the ClaudeAI API with the prompt and return the response
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "claude-2");
            requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));
            requestBody.put("max_tokens", 1000);
            requestBody.put("temperature", 0.7);
            // Use your preferred HTTP client to send the request to ClaudeAI API
            // Example: HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            // Parse the response and return the analysis result

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("accept","application/json");
            headers.set("x-litellm-api-key", apiKey);
            // Implement the HTTP request to ClaudeAI API using the headers and requestBody
           
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            // Example: ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity

            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, org.springframework.http.HttpMethod.POST, entity, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return extractAiResponse(response.getBody());
            } else {
                // Handle non-OK response
                return "Error: Received non-OK response from ClaudeAI API";
            }
        }
        catch(Exception e){
            // Handle exceptions
           throw new RuntimeException("Error calling ClaudeAI API: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isAvailable() {
        // Implement logic to check if ClaudeAI service is available
        return apiKey != null && !apiKey.isEmpty() && apiUrl != null && !apiUrl.isEmpty();
    }

    private String buildReviewPrompt(String prTitle, String prDiff, int prNumber, String repository) {
        return String.format(
                "You are an expert code reviewer. Please review the following pull request and provide feedback:\n\n" +
                "Repository: %s\n" +
                "PR Title: %s\n" +
                "PR Number: %d\n" +
                "Diff:\n%s\n\n" +
                "Please provide a detailed review of the code changes, including any potential issues, improvements, and overall feedback.",
                repository, prTitle, prNumber, prDiff
        );
    }

    private String extractAiResponse(Map<String, Object> responseBody) {
        // Implement logic to extract the AI response from the API response body
        // This will depend on the structure of the response returned by ClaudeAI API
        // Example: return (String) responseBody.get("choices").get(0).get("message").get("content");
        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            if (message != null) {
                return (String) message.get("content");
            }
        }
        return "Error: Unable to extract AI response from ClaudeAI API response";
    }

    private RestTemplate createRestTemplate() {
        // Create and configure RestTemplate if needed (e.g., for timeouts, interceptors, etc.)
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new javax.net.ssl.X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                    public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            return new RestTemplate();
        } catch (Exception e) {
           return new RestTemplate();
        }
    }
    
}
