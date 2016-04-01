package com.karumi.reddo.task;

import com.karumi.reddo.github.GitHubApiClient;
import com.karumi.reddo.github.GitHubRepository;

import com.karumi.reddo.log.Log;
import java.util.List;

public class GitHubUserTask implements ReddoTask {

  private final String user;
  private final GitHubApiClient gitHubApiClient;

  public GitHubUserTask(String user, GitHubApiClient gitHubApiClient) {
    this.user = user;
    this.gitHubApiClient = gitHubApiClient;
  }

  @Override
  public String execute() {
    List<GitHubRepository> userRepositories = gitHubApiClient.getUserRepositories(user);
    Log.d("Repositories info downloaded for user: " + user);
    return userRepositories.toString();
  }
}
