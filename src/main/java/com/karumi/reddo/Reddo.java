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

package com.karumi.reddo;

import com.karumi.reddo.task.ReddoTask;
import com.karumi.reddo.view.View;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Reddo {

  private final View view;
  private final Collection<ReddoTask> tasks;

  public Reddo(View view) {
    this.view = view;
    this.tasks = new LinkedList<>();
  }

  public void addTasks(Collection<ReddoTask> tasks) {
    this.tasks.addAll(tasks);
  }

  public void evaluateTasks() {
    List<String> reddoTaskResults = executeTasks();
    showResults(reddoTaskResults);
  }

  private List<String> executeTasks() {
    return tasks.stream()
        .parallel()
        .map(ReddoTask::execute)
        .filter(result -> result != null && !result.isEmpty())
        .collect(Collectors.toList());
  }

  private void showResults(List<String> reddoTaskResults) {
    view.showMessages(reddoTaskResults);
  }
}
