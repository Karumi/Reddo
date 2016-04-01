package com.karumi.reddo;

import com.karumi.reddo.config.ReddoConfig;
import com.karumi.reddo.config.TypesafeHubReddoConfig;
import com.karumi.reddo.log.Log;
import com.karumi.reddo.task.ReddoTask;
import com.karumi.reddo.view.View;

import com.karumi.reddo.view.exceptions.MatrixLedViewException;
import java.util.Collection;

public class Main {

  public static void main(String[] args) {
    Log.d("Let's gonna start!!!");
    ReddoConfig config = new TypesafeHubReddoConfig();
    View view = config.getView();
    Reddo reddo = new Reddo(view);
    Collection<ReddoTask> tasksFromConfig = config.getTasks();
    reddo.addTasks(tasksFromConfig);
    Log.d(tasksFromConfig.size() + " tasks loaded.");
    while (true) {
      try {
        reddo.evaluateTasks();
      } catch (MatrixLedViewException e) {
        Log.e(e.toString());
      }
    }
  }
}
