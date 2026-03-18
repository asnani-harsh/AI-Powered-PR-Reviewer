package com.prbot.service.ai;

public interface AIProvider {
    String getName();
    String analyzeCode(String prTitle, String prDiff, int prNumber, String repository);
    boolean isAvailable();
}
