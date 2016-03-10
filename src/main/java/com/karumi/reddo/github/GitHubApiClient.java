package com.karumi.reddo.github;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class GitHubApiClient {

  private static GitHub gitHub;

  public static void setOauthToken(String oauthToken) {
    validateOauthToken(oauthToken);

    try {
      gitHub = GitHub.connectUsingOAuth(oauthToken);
    } catch (IOException e) {
      System.out.println("Error connecting to the GitHub API using the oauth token " + oauthToken);
    }
  }

  public GitHubRepository getRepository(String name) {
    validateGitHubConfiguration();
    validateRepositoryName(name);

    GitHubRepository repository = null;
    try {
      GHRepository gitHubRepository = gitHub.getRepository(name);
      repository = mapGhRepository(gitHubRepository);
    } catch (IOException e) {
      System.out.println("Error retrieving repository " + name);
    }
    return repository;
  }

  public List<GitHubRepository> getUserRepositories(String user) {
    validateUserName(user);
    List<GitHubRepository> repositories = new LinkedList<>();
    try {
      Map<String, GHRepository> gitHubRepositories = gitHub.getUser(user).getRepositories();
      repositories = mapGhRepositories(gitHubRepositories.values());
    } catch (IOException e) {
      System.out.println("Error retrieving repositories from user " + user);
    }
    return repositories;
  }

  private List<GitHubRepository> mapGhRepositories(Collection<GHRepository> gitHubRepositories)
      throws IOException {
    List<GitHubRepository> repositories = new LinkedList<>();
    for (GHRepository repository : gitHubRepositories) {
      repositories.add(mapGhRepository(repository));
    }
    return repositories;
  }

  private GitHubRepository mapGhRepository(GHRepository gitHubRepository) throws IOException {
    String repositoryName = gitHubRepository.getName();
    int stars = gitHubRepository.listStargazers().asList().size();
    int openPullRequests = gitHubRepository.getPullRequests(GHIssueState.OPEN).size();
    int pullRequests =
        openPullRequests + gitHubRepository.getPullRequests(GHIssueState.CLOSED).size();
    int openIssues = gitHubRepository.getOpenIssueCount();
    int branches = gitHubRepository.getBranches().size();
    int collaborators = gitHubRepository.getCollaborators().size();
    int forks = gitHubRepository.getForks();
    int watchers = gitHubRepository.getWatchers();

    return new GitHubRepository(repositoryName, stars, openPullRequests, pullRequests, openIssues,
        branches, collaborators, forks, watchers);
  }

  private static void validateOauthToken(String oauthToken) {
    if (oauthToken == null || oauthToken.isEmpty()) {
      throw new IllegalArgumentException("The oauth token can not be null or empty.");
    }
  }

  private void validateGitHubConfiguration() {
    if (gitHub == null) {
      throw new IllegalStateException("The GitHub client has not been configured.");
    }
  }

  private void validateRepositoryName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("The repository name can not be null or empty.");
    }
  }

  private void validateUserName(String user) {
    if (user == null || user.isEmpty()) {
      throw new IllegalArgumentException("The user name can not be null or empty.");
    }
  }
}
