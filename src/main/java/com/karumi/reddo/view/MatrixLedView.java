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

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MatrixLedView implements View {

    public static final int LED_HEIGHT = 32;

    private final int fps;

    public MatrixLedView(int fps) {
        this.fps = fps;
    }

    @Override
    public void showMessages(List<String> messages) {
        String joinMessages = String.join(" --- ", messages);
        int stringWidthInPixels = getStringWidthInPixels(joinMessages);
        BufferedImage outputImage = getJoinMessagesAsImage(joinMessages, stringWidthInPixels);
        drawImage(outputImage);
    }

    private void drawImage(BufferedImage outputImage) {
        int width = outputImage.getWidth();
        JFrame frame = new JFrame();
        JPanel comp = new JPanel();
        frame.add(comp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(16, 32 + 22);
        frame.setVisible(true);
        for (int i = 0; i < width; i++) {
            frame.getGraphics().drawImage(outputImage, -i, 22, null);
            waitForNextFrame();
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
        BufferedImage outputImage = new BufferedImage(stringWidthInPixels, LED_HEIGHT, BufferedImage.TYPE_INT_RGB);
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


}
