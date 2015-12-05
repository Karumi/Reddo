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
