package com.karumi.reddo.view;

import java.util.List;

public class SysOutView implements View {

  @Override
  public void showMessages(List<String> messages) {
    messages.forEach(System.out::println);
  }
}
