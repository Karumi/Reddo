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

import com.karumi.reddo.config.ReddoConfig;
import com.karumi.reddo.config.TypesafeHubReddoConfig;
import com.karumi.reddo.task.MessageTask;
import com.karumi.reddo.view.SysOutView;

public class Main {

  public static void main(String[] args) {
    ReddoConfig config = new TypesafeHubReddoConfig();
    Reddo reddo = new Reddo(config, new SysOutView());
    reddo.addTask(new MessageTask("Hola"));
    reddo.addTask(new MessageTask("Pedro"));
    reddo.addTask(new MessageTask("Vicente"));
    reddo.addTask(new MessageTask("Gómez"));
    reddo.addTask(new MessageTask("Sánchez"));
    reddo.start();
  }

}
