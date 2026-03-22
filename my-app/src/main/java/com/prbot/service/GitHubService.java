package com.prbot.service;

import com.prbot.exception.GitHubApiException;
import com.prbot.exception.ResourceNotFoundException;
import com.prbot.model.PullRequestDTO;
import com.prbot.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubService {
    private final GitHub github;

    // public List<PullRequestDTO> getPullRequests(String owner, String repo, String state) {
    //     try {
    //         GHRepository repository = github.getRepository(owner + "/" + repo);
    //         GHIssueState issueState = mapState(state);
    //         List<GHPullRequest> pullRequests = repository.getPullRequests(issueState);
    //         return pullRequests.stream()
    //                 .map(this::mapToDTO)
    //                 .collect(Collectors.toList());
    //     } catch (Exception e) {
    //         throw new RuntimeException("Failed to fetch pull requests: " + e.getMessage(), e);
    //     }
    // }

    public PullRequestDTO getPullRequestByNumber(String owner, String repo, int prNumber) {
        try {
            GHRepository repository = github.getRepository(owner + "/" + repo);
            GHPullRequest pr = repository.getPullRequest(prNumber);
            if (pr == null) {
                throw new RuntimeException("Pull request not found: " + prNumber);
            }
            return mapToDTO(pr);
        } catch (Exception e) {
            throw new GitHubApiException("Failed to fetch pull request: " + e.getMessage(), e);
        }
    }

    public String getPullRequestDiff(String owner, String repo, int prNumber) {
        try {
            GHRepository repository = github.getRepository(owner + "/" + repo);
            GHPullRequest pr = repository.getPullRequest(prNumber);
            if (pr == null) {
                throw new ResourceNotFoundException("Pull request not found: " + prNumber);
            }

            StringBuilder diffContent = new StringBuilder();
            diffContent.append("Pull Request #").append(pr.getNumber()).append(": ").append(pr.getTitle()).append("\n");
            diffContent.append("Base: ").append(pr.getBase().getLabel()).append(" <- Head: ").append(pr.getHead().getLabel()).append("\n\n");
            diffContent.append("Files changed: ").append(pr.getChangedFiles()).append("\n");
            diffContent.append("Additions: ").append(pr.getAdditions()).append(" Deletions: ").append(pr.getDeletions()).append("\n\n");

            diffContent.append("FILES CHANGED:\n");
            for (GHPullRequestFileDetail file : pr.listFiles()) {
                diffContent.append("Filename: ").append(file.getFilename()).append("\n");
                diffContent.append("Status: ").append(file.getStatus()).append("\n");
                diffContent.append("Additions: ").append(file.getAdditions()).append(" Deletions: ").append(file.getDeletions()).append("\n");
                diffContent.append("Changes: ").append(file.getChanges()).append("\n");
                diffContent.append("Patch:\n").append(file.getPatch()).append("\n\n");
            }
            return diffContent.toString();
        } catch (Exception e) {
            throw new GitHubApiException("Failed to fetch pull request diff: " + e.getMessage(), e);
        }
    }

    public void addCommentToPullRequest(String owner, String repo, int prNumber, String comment) {
        try {
            GHRepository repository = github.getRepository(owner + "/" + repo);
            GHPullRequest pr = repository.getPullRequest(prNumber);

            if (pr == null) {
                throw new ResourceNotFoundException("Pull request not found: " + prNumber);
            }
            pr.comment(comment);
        } catch (Exception e) {
            throw new GitHubApiException("Failed to add comment to pull request: " + e.getMessage(), e);
        }
    }

    public boolean validateRepository(String owner, String repo) {
        try {
            GHRepository repository = github.getRepository(owner + "/" + repo);
            return repository != null;
        } catch (IOException e) {
            return false;
        }
    }

    private PullRequestDTO mapToDTO(GHPullRequest pr) {
        try {
            return PullRequestDTO.builder()
                    .number(pr.getNumber())
                    .title(pr.getTitle())
                    .body(pr.getBody())
                    .state(pr.getState().name())
                    .createdAt(parseDate(pr.getCreatedAt()))
                    .updatedAt(parseDate(pr.getUpdatedAt()))
                    .closedAt(parseDate(pr.getClosedAt()))
                    .mergedAt(parseDate(pr.getMergedAt()))
                    .mergeable(pr.isMergeable())
                    .merged(pr.isMerged())
                    .mergeableState(pr.getMergeableState())
                    .additions(pr.getAdditions())
                    .deletions(pr.getDeletions())
                    .changedFiles(pr.getChangedFiles())
                    .build();
        } catch (Exception e) {
            throw new GitHubApiException("Failed to map pull request to DTO: " + e.getMessage(), e);
        }
    }

    private UserDTO mapUserToDTO(GHUser user) {
        try {
            return UserDTO.builder()
                    .login(user.getLogin())
                    .name(user.getName())
                    .email(user.getEmail())
                    .avatarUrl(user.getAvatarUrl())
                    .build();
        } catch (Exception e) {
            throw new GitHubApiException("Failed to map user to DTO: " + e.getMessage(), e);
        }
    }

    private GHIssueState mapState(String state) {
        if (state == null) {
            return GHIssueState.OPEN;
        }
        switch (state.toLowerCase()) {
            case "open":
                return GHIssueState.OPEN;
            case "closed":
                return GHIssueState.CLOSED;
            case "all":
                return GHIssueState.ALL;
            default:
                return GHIssueState.OPEN;
        }
    }

    private LocalDateTime parseDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
