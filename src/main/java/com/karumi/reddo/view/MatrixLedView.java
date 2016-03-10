package com.karumi.reddo.view;

import com.karumi.reddo.view.exceptions.MatrixLedViewException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MatrixLedView implements View {

  private static final int LED_HEIGHT = 16;
  private static final int MIN_FPS = 20;

  private final int fps;
  private final String ip;
  private final int port;
  private Socket socket;

  public MatrixLedView(int fps, String ip, int port) {
    validateFps(fps);
    this.fps = fps;
    this.ip = ip;
    this.port = port;
  }

  @Override public void showMessages(List<String> messages) {
    String joinMessages = String.join(" --- ", messages);
    int stringWidthInPixels = getStringWidthInPixels(joinMessages);
    BufferedImage outputImage = getJoinMessagesAsImage(joinMessages, stringWidthInPixels);
    drawImage(outputImage);
  }

  private void drawImage(BufferedImage outputImage) {
    Socket socket = getSocket();
    try {
      DataOutputStream stream = new DataOutputStream(socket.getOutputStream());

      for (int frame = 0; frame < outputImage.getWidth() - 32; frame++) {
        drawFrame(outputImage.getSubimage(frame, 0, 32, 16), stream);
        waitForNextFrame();
      }
    } catch (IOException e) {
      throw new MatrixLedViewException(e.getMessage());
    }
  }

  private void drawFrame(BufferedImage outputImage, DataOutputStream stream) throws IOException {
    stream.writeInt(outputImage.getHeight());
    stream.writeInt(outputImage.getWidth());

    for (int y = 0; y < outputImage.getHeight(); y++) {
      for (int x = 0; x < outputImage.getWidth(); x++) {
        int rgb = outputImage.getRGB(x, y);
        stream.writeInt(rgb);
      }
    }
  }

  private void waitForNextFrame() {
    float frameTime = 1 / (float) fps * 1000;
    try {
      Thread.sleep((long) frameTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
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
      throw new IllegalArgumentException("The configured fps can't be less than 60");
    }
  }

  private Socket getSocket() {
    if (socket == null) {
      socket = createSocket();
    }

    return socket;
  }

  private Socket createSocket() {
    try {
      return new Socket(ip, port);
    } catch (IOException e) {
      throw new MatrixLedViewException("Impossible to create a socket to " + ip + ":" + port);
    }
  }
}
