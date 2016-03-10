package com.karumi.reddo;

import com.karumi.reddo.config.ReddoConfig;
import com.karumi.reddo.config.TypesafeHubReddoConfig;
import com.karumi.reddo.task.ReddoTask;
import com.karumi.reddo.view.View;
import java.util.Collection;

public class Main {

  public static void main(String[] args) {
    ReddoConfig config = new TypesafeHubReddoConfig();
    View view = config.getView();
    Reddo reddo = new Reddo(view);
    Collection<ReddoTask> tasksFromConfig = config.getTasks();
    reddo.addTasks(tasksFromConfig);
    while (true) {
      reddo.evaluateTasks();
    }
  }
}
