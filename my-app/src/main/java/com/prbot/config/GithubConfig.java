package com.prbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.prbot.GithubPrServiceApplication;

import lombok.Data;

@Configuration
public class GithubConfig {
    @Bean
    public Github github(GithubProperties properties) {
        return new GithubBuilder().withOAuthToken(properties.getToken()).build();
    }
}

@Component
@ConfigurationProperties(prefix = "github")
@Data
class GithubProperties {
    private String token;
    private Api api = new Api();

    @Data
    public static class Api {
        private String baseUrl;
    }
}
