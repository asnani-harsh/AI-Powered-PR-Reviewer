package com.prbot.service.ai;

public class AiProviderFactory {
    private final ClaudeAIProvider claudeProvider;

    public AIProvider getProvider() {
        if (claudeProvider.isAvailable()) {
            return claudeProvider;
        }
        throw new RuntimeException("No AI provider is currently available");
    }   

    public AIProvider getProvideerByName(String providerName) {
       return claudeProvider;
    }
}
