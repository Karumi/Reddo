# -*- coding: utf-8 -*-

import os


# This display is meant for debug purposes only.
# It renders every frame in the console, it's supposed to be used to test the
# daemon in your raspberry or remote session
class Display:

    PIXEL = "◼◼"
    EMPTY_PIXEL = "  "
    R = "\033[91m"
    G = "\033[92m"
    B = "\033[94m"
    DEFAULT = "\033[0m"

    def __init__(self):
        self.width = 32
        self.height = 16

    def render(self, frame):
        frame_str = ""
        for row in range(frame.height):
            for col in range(frame.width):
                pixel = frame.get_pixel(col, row)
                if pixel.red > 0:
                    frame_str += Display.R + Display.PIXEL + Display.DEFAULT
                elif pixel.green > 0:
                    frame_str += Display.G + Display.PIXEL + Display.DEFAULT
                elif pixel.blue > 0:
                    frame_str += Display.B + Display.PIXEL + Display.DEFAULT
                else:
                    frame_str += Display.DEFAULT + Display.EMPTY_PIXEL
            frame_str += "\n"

        os.system('clear')
        print(frame_str)

    def clean(self):
        os.system('clear')
