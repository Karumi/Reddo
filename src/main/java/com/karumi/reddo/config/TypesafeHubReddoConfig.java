package com.karumi.reddo.config;

import com.karumi.reddo.api.OkHttpReddoApiClient;
import com.karumi.reddo.api.ReddoApiClient;
import com.karumi.reddo.github.GitHubApiClient;
import com.karumi.reddo.task.GitHubRepositoryTask;
import com.karumi.reddo.task.GitHubUserTask;
import com.karumi.reddo.task.MessageTask;
import com.karumi.reddo.task.ReddoTask;
import com.karumi.reddo.view.MatrixLedView;
import com.karumi.reddo.view.SysOutView;
import com.karumi.reddo.view.View;
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
    config.checkValid(config, CONFIG_NAME);
  }

  @Override public List<ReddoTask> getTasks() {
    List<ReddoTask> tasks = new LinkedList<>();
    tasks.addAll(getMessagesTasks());
    tasks.addAll(getGitHubRepositoriesTasks());
    tasks.addAll(getGitHubUsersTasks());
    return tasks;
  }

  @Override public View getView() {
    int fps = getFramesPerSecond();
    switch (config.getString("output")) {
      case "led":
        ReddoApiClient apiClient =
            new OkHttpReddoApiClient(getConnectionRemoteIp(), getConnectionRemotePort());
        return new MatrixLedView(fps, apiClient);
      case "sysout":
      default:
        return new SysOutView();
    }
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

  private int getFramesPerSecond() {
    return config.getInt("fps");
  }

  private String getConnectionRemoteIp() {
    return config.getString("connection.remoteIp");
  }

  private int getConnectionRemotePort() {
    return config.getInt("connection.remotePort");
  }

  private File getConfigFile() {
    String currentPath = System.getProperty("user.dir");
    return new File(currentPath + File.separator + CONFIG_PATH);
  }
}
