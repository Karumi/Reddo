# -*- coding: utf-8 -*-

import base64
import pixel


class Frame:
    def __init__(self, width, height, pixels):
        self.width = width
        self.height = height
        self.pixels = pixels

    def get_subframe(self, x_offset, width):
        pixels = [self.get_pixel(x, y)
                  for y in range(self.height)
                  for x in range(x_offset, x_offset + width)]
        return Frame(width, self.height, pixels)

    def get_pixel(self, x, y):
        if x < 0 or x >= self.width or y < 0 or y >= self.height:
            return None

        return self.pixels[y * self.width + x]

    def __repr__(self):
        frame_str = ""
        for row in range(self.height):
            for col in range(self.width):
                pixel = self.get_pixel(col, row)
                if pixel.red > 0 or pixel.green > 0 or pixel.blue > 0:
                    frame_str += "◼"
                else:
                    frame_str += "◻"
            frame_str += "\n"
        return frame_str


class FrameReader:
    def read(self, raw_frame):
        width = raw_frame["width"]
        height = raw_frame["height"]
        raw_data = base64.b64decode(raw_frame["pixels"])
        pixels = [pixel.Pixel(ord(raw_data[i]),
                              ord(raw_data[i + 1]),
                              ord(raw_data[i + 2]))
                  for i in range(0, len(raw_data), 3)]
        frame = Frame(width, height, pixels)
        return frame
