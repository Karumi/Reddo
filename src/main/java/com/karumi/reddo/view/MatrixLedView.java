package com.karumi.reddo.view;

import com.karumi.reddo.api.Base64Image;
import com.karumi.reddo.api.ReddoApiClient;

import com.karumi.reddo.log.Log;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class MatrixLedView implements View {

  private static final int LED_HEIGHT = 16;
  private static final int MIN_FPS = 20;
  private static final int COLOR_COUNT = 3;

  private final int fps;
  private final ReddoApiClient apiClient;

  public MatrixLedView(int fps, ReddoApiClient apiClient) {
    validateFps(fps);
    this.fps = fps;
    this.apiClient = apiClient;
  }

  @Override
  public void showMessages(List<String> messages) {
    String joinMessages = String.join(" --- ", messages);
    int stringWidthInPixels = getStringWidthInPixels(joinMessages);
    BufferedImage outputImage = getJoinMessagesAsImage(joinMessages, stringWidthInPixels);
    drawImage(outputImage);
    waitForNextFrame(outputImage.getWidth());
  }

  private void drawImage(BufferedImage outputImage) {
    String base64Image = generateBase64Image(outputImage);
    int width = outputImage.getWidth();
    int height = outputImage.getHeight();
    Base64Image image = new Base64Image(width, height, base64Image);
    try {
      apiClient.enqueueImage(fps, image);
    } catch (IOException e) {
      Log.e(e.toString());
    }
  }

  private String generateBase64Image(BufferedImage outputImage) {
    byte[] data = new byte[COLOR_COUNT * outputImage.getWidth() * outputImage.getHeight()];
    for (int y = 0; y < outputImage.getHeight(); y++) {
      for (int x = 0; x < outputImage.getWidth(); x++) {
        int rgb = outputImage.getRGB(x, y);
        data[x * COLOR_COUNT + y * (COLOR_COUNT * outputImage.getWidth())] = (byte) (rgb & 0xFF0000);
        data[x * COLOR_COUNT + y * (COLOR_COUNT * outputImage.getWidth()) + 1] = (byte) (rgb & 0x00FF00);
        data[x * COLOR_COUNT + y * (COLOR_COUNT * outputImage.getWidth()) + 2] = (byte) (rgb & 0x0000FF);
      }
    }
    return Base64.getEncoder().encodeToString(data);
  }

  private void waitForNextFrame(int imageWidth) {
    float frameTime = 1 / (float) fps * 1000;
    float imageTime = frameTime * imageWidth;
    try {
      Thread.sleep((long) imageTime);
    } catch (InterruptedException e) {
      Log.e(e.toString());
    }
  }

  private BufferedImage getJoinMessagesAsImage(String joinMessages, int stringWidthInPixels) {
    BufferedImage outputImage =
        new BufferedImage(stringWidthInPixels, LED_HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = outputImage.createGraphics();
    graphics.setFont(getFont());
    graphics.setColor(Color.BLUE);
    graphics.drawString(joinMessages, 0, LED_HEIGHT - 5);
    return outputImage;
  }

  private int getStringWidthInPixels(String joinMessages) {
    Graphics graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
    Font font = getFont();
    graphics.setFont(font);
    return graphics.getFontMetrics().stringWidth(joinMessages);
  }

  private Font getFont() {
    return new Font(Font.MONOSPACED, Font.PLAIN, LED_HEIGHT - 2);
  }

  private void validateFps(int fps) {
    if (fps < MIN_FPS) {
      throw new IllegalArgumentException("The configured fps can't be less than " + MIN_FPS );
    }
  }


}
