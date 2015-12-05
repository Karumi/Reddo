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

package com.karumi.reddo.task;

import com.karumi.reddo.github.GitHubApiClient;
import com.karumi.reddo.github.GitHubRepository;
import java.util.List;

public class GitHubUserTask implements ReddoTask {

  private final String user;
  private final GitHubApiClient gitHubApiClient;

  public GitHubUserTask(String user, GitHubApiClient gitHubApiClient) {
    this.user = user;
    this.gitHubApiClient = gitHubApiClient;
  }

  @Override public String execute() {
    List<GitHubRepository> userRepositories = gitHubApiClient.getUserRepositories(user);
    return userRepositories.toString();
  }
}
