package com.prbot.controller;

import java.util.HashMap;
import java.util.Map;

import com.prbot.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {
    @GetMapping
        public ResponseEntity<ApiResponse<Map<String, String>>> health() {
            Map<String, String> healthStatus = new HashMap<>();
            healthStatus.put( "status", "UP");
            healthStatus.put( "service",  "Github PR Service");
            healthStatus.put( "version",  "1.0.0");

            return ResponseEntity.ok(ApiResponse.success(healthStatus));
            
        }
}
