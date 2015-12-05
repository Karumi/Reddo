/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karumi.reddo.github;

public class GitHubRepository {

  private final String repositoryName;
  private final int numberOfStars;
  private final int numberOfOpenPullRequests;
  private final int numberOfPullRequests;

  public GitHubRepository(String repositoryName, int numberOfStars, int numberOfOpenPullRequests,
      int numberOfPullRequests) {
    this.repositoryName = repositoryName;
    this.numberOfStars = numberOfStars;
    this.numberOfOpenPullRequests = numberOfOpenPullRequests;
    this.numberOfPullRequests = numberOfPullRequests;
  }

  public String getRepositoryName() {
    return repositoryName;
  }

  public int getNumberOfStars() {
    return numberOfStars;
  }

  public int getNumberOfOpenPullRequests() {
    return numberOfOpenPullRequests;
  }

  public int getNumberOfPullRequests() {
    return numberOfPullRequests;
  }

  @Override public String toString() {
    return "GitHubRepository{" +
        "repositoryName='" + repositoryName + '\'' +
        ", numberOfStars=" + numberOfStars +
        ", numberOfOpenPullRequests=" + numberOfOpenPullRequests +
        ", numberOfPullRequests=" + numberOfPullRequests +
        '}';
  }
}
