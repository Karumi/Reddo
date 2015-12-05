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

package com.karumi.reddo.config;

import com.karumi.reddo.github.GitHubApiClient;
import com.karumi.reddo.task.GitHubRepositoryTask;
import com.karumi.reddo.task.GitHubUserTask;
import com.karumi.reddo.task.MessageTask;
import com.karumi.reddo.task.ReddoTask;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TypesafeHubReddoConfig implements ReddoConfig {

  private static final String DEFAULT_CONFIG_PATH = "default-reddo-config";
  private static final String CONFIG_PATH = "reddo-config.json";
  private static final String CONFIG_NAME = "reddo-config";

  private final Config config;
  private GitHubApiClient gitHubApiClient;

  public TypesafeHubReddoConfig() {
    File configFile = getConfigFile();
    this.config = ConfigFactory.
        parseFile(configFile).withFallback(ConfigFactory.load(DEFAULT_CONFIG_PATH));
    config.checkValid(config, new String[] { CONFIG_NAME });
  }

  @Override public int getFramesPerSecond() {
    return config.getInt("fps");
  }

  @Override public List<ReddoTask> getConfiguredTasks() {
    List<ReddoTask> tasks = new LinkedList<>();
    tasks.addAll(getGitHubRepositoriesTasks());
    tasks.addAll(getMessagesTasks());
    tasks.addAll(getGitHubUsersTasks());
    return tasks;
  }

  private String getGitHubOauthToken() {
    return config.getString("gitHubOauthToken");
  }

  private List<ReddoTask> getGitHubRepositoriesTasks() {
    GitHubApiClient gitHubApiClient = getGitHubApiClient();
    List<String> repositories = config.getStringList("gitHubRepositories");
    return repositories.stream()
        .map(repositoryName -> new GitHubRepositoryTask(repositoryName, gitHubApiClient))
        .collect(Collectors.toList());
  }

  private Collection<? extends ReddoTask> getMessagesTasks() {
    List<String> messages = config.getStringList("messages");
    return messages.stream().map(MessageTask::new).collect(Collectors.toList());
  }

  private Collection<? extends ReddoTask> getGitHubUsersTasks() {
    GitHubApiClient gitHubApiClient = getGitHubApiClient();
    List<String> users = config.getStringList("gitHubUsers");
    return users.stream()
        .map(userName -> new GitHubUserTask(userName, gitHubApiClient))
        .collect(Collectors.toList());
  }

  private GitHubApiClient getGitHubApiClient() {
    if (gitHubApiClient == null) {
      GitHubApiClient.setOauthToken(getGitHubOauthToken());
      gitHubApiClient = new GitHubApiClient();
    }
    return gitHubApiClient;
  }

  private File getConfigFile() {
    String currentPath = System.getProperty("user.dir");
    return new File(currentPath + File.separator + CONFIG_PATH);
  }
}
