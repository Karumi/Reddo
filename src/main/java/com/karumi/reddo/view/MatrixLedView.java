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

package com.karumi.reddo.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import javax.net.SocketFactory;
import javax.swing.*;

public class MatrixLedView implements View {

  private static final int LED_HEIGHT = 32;
  private static final int MIN_FPS = 60;

  private final int fps;
  private final Socket socket;

  public MatrixLedView(int fps, Socket socket) {
    validateFps(fps);
    this.fps = fps;
    this.socket = socket;
  }

  @Override public void showMessages(List<String> messages) {
    String joinMessages = String.join(" --- ", messages);
    int stringWidthInPixels = getStringWidthInPixels(joinMessages);
    BufferedImage outputImage = getJoinMessagesAsImage(joinMessages, stringWidthInPixels);
    drawImage(outputImage);
  }

  private void drawImage(BufferedImage outputImage) {
    try {
      System.out.println("Sending image (" + outputImage.getHeight() + ",  " + outputImage.getWidth() + ")");
      DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
      stream.writeInt(outputImage.getHeight());
      stream.writeInt(outputImage.getWidth());
      for (int y = 0; y < outputImage.getHeight(); y++) {
        for (int x = 0; x < outputImage.getWidth(); x++) {
          stream.writeInt(outputImage.getRGB(x, y));
        }
      }
      stream.flush();
      stream.close();
      socket.close();
    } catch (IOException e) {
      // ... for real, I'm fucking tired of IOExceptions everywhere, I'm gonna plug every socket
      // logic in a single class replacing all those IOExceptions with unchecked exceptions
      throw new RuntimeException("Ole tus cojones morenos ya hombre... " + e.getMessage());
    }

    System.exit(0);


//    int width = outputImage.getWidth();
//    JFrame frame = new JFrame();
//    JPanel comp = new JPanel();
//    frame.add(comp);
//    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//    frame.setSize(16, 32 + 22);
//    frame.setVisible(true);
//    for (int i = 0; i < width; i++) {
//      frame.getGraphics().drawImage(outputImage, -i, 22, null);
//      waitForNextFrame();
//    }
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
    return new Font("Serif", Font.PLAIN, LED_HEIGHT - 2);
  }

  private void validateFps(int fps) {
    if (fps < MIN_FPS) {
      throw new IllegalArgumentException("The configured fps can't be less than 60");
    }
  }
}
