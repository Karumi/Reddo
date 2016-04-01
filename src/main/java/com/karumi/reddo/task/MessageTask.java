package com.karumi.reddo.task;

import com.karumi.reddo.log.Log;

public class MessageTask implements ReddoTask {

  private final String messsage;

  public MessageTask(String messsage) {
    this.messsage = messsage;
  }
  
  @Override public String execute() {
    Log.d("Message loaded: " + messsage);
    return messsage;
  }
}