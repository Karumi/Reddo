package com.karumi.reddo.task;

public class MessageTask implements ReddoTask {

  private final String messsage;

  public MessageTask(String messsage) {
    this.messsage = messsage;
  }
  
  @Override public String execute() {
    System.out.println("Message loaded: " + messsage);
    return messsage;
  }
}