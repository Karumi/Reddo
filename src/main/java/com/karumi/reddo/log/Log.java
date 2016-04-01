package com.karumi.reddo.log;

import org.apache.log4j.Logger;

public class Log {

  private final static Logger logger = Logger.getLogger("Reddo");

  public static void d(String message) {
    logger.debug(message);
  }

  public static void e(String message) {
    logger.error(message);
  }
}
