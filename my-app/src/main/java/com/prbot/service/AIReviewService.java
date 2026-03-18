package com.prbot.service;

import org.springframework.stereotype.Service;

@Service
public class AIReviewService {

    public String analyzePullRequest(String title, String diff, int prNumber, String repoFullName) {
        // TODO: implement AI review logic (e.g., call ClaudeAIProvider or other provider)
        return "AI review is not implemented yet.";
    }
}
