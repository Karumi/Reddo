package com.karumi.reddo.github;

public class GitHubRepository {

  private final String name;
  private final int stars;
  private final int openPullRequests;
  private final int pullRequests;
  private final int openIssues;
  private final int branches;
  private final int collaborators;
  private final int forks;
  private final int watchers;

  public GitHubRepository(String name, int stars, int openPullRequests, int pullRequests,
      int openIssues, int branches, int collaborators, int forks, int watchers) {
    this.name = name;
    this.stars = stars;
    this.openPullRequests = openPullRequests;
    this.pullRequests = pullRequests;
    this.openIssues = openIssues;
    this.branches = branches;
    this.collaborators = collaborators;
    this.forks = forks;
    this.watchers = watchers;
  }

  public String getName() {
    return name;
  }

  public int getStars() {
    return stars;
  }

  public int getOpenPullRequests() {
    return openPullRequests;
  }

  public int getPullRequests() {
    return pullRequests;
  }

  public int getOpenIssues() {
    return openIssues;
  }

  public int getBranches() {
    return branches;
  }

  public int getCollaborators() {
    return collaborators;
  }

  public int getForks() {
    return forks;
  }

  public int getWatchers() {
    return watchers;
  }

  @Override public String toString() {
    return
        "" + name + " -> " +
        " ★ : " + stars +
        ", Issues : " + openIssues +
        ", Open PRs : " + openPullRequests +
        ", PRs : " + pullRequests;
  }
}