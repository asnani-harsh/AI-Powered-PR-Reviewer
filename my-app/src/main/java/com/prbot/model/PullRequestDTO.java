package com.prbot.model;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PullRequestDTO {
    private Long id;
    private Integer number;
    private String state;
    private String title;
    private String body;
    private String author;
    private String htmlUrl;
    private String baseBranch;
    private String headBranch;
    private Integer commitsCount;
    private Integer changedFiles;
    private Integer additions;
    private Integer deletions;
    private List<String> labels;
    private Boolean draft;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime mergedAt;
         
}
