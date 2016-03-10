package com.karumi.reddo.config;

import com.karumi.reddo.task.ReddoTask;
import com.karumi.reddo.view.View;
import java.util.List;

public interface ReddoConfig {

  List<ReddoTask> getTasks();

  View getView();
}
