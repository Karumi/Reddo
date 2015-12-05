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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;

public class TypesafeHubReddoConfig implements ReddoConfig {

  private static final String DEFAULT_CONFIG_PATH = "default-reddo-config";
  private static final String CONFIG_PATH = "reddo-config.json";
  private static final String CONFIG_NAME = "reddo-config";

  private final Config config;

  public TypesafeHubReddoConfig() {
    File configFile = getConfigFile();
    this.config = ConfigFactory.
        parseFile(configFile).withFallback(ConfigFactory.load(DEFAULT_CONFIG_PATH));
    config.checkValid(config, new String[] { CONFIG_NAME });
  }

  @Override public int getFramesPerSecond() {
    return config.getInt("fps");
  }

  @Override public String getGitHubOauthToken() {
    return config.getString("gitHubOauthToken");
  }

  private File getConfigFile() {
    String currentPath = System.getProperty("user.dir");
    return new File(currentPath + File.separator + CONFIG_PATH);
  }
}
