package com.karumi.reddo.api;

public class Base64Image {

  private final int width;
  private final int height;
  private final String content;

  public Base64Image(int width, int height, String content) {
    this.width = width;
    this.height = height;
    this.content = content;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getContent() {
    return content;
  }
}
