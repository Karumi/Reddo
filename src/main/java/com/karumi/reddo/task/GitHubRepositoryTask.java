package com.karumi.reddo.task;

import com.karumi.reddo.github.GitHubApiClient;
import com.karumi.reddo.github.GitHubRepository;

public class GitHubRepositoryTask implements ReddoTask {

  private final String repositoryName;
  private final GitHubApiClient gitHubApiClient;

  public GitHubRepositoryTask(String repositoryName, GitHubApiClient gitHubApiClient) {
    this.repositoryName = repositoryName;
    this.gitHubApiClient = gitHubApiClient;
  }

  @Override public String execute() {
    GitHubRepository repository = gitHubApiClient.getRepository(repositoryName);
    return repository.toString();
  }
}
