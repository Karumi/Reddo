import os
import curses


# This display is meant for debug purposes only.
# It renders every frame in a curses session, meant to be used in local.
class Display:

    PIXEL = "O"
    EMPTY_PIXEL = " "

    def __init__(self):
        self.width = 32
        self.height = 16
        self.stdscr = curses.initscr()

    def render(self, frame):
        frame_str = ""
        for row in range(frame.height):
            for col in range(frame.width):
                pixel = frame.get_pixel(col, row)
                if pixel.red > 0 or pixel.green > 0 or pixel.blue > 0:
                    frame_str += Display.PIXEL
                else:
                    frame_str += Display.EMPTY_PIXEL
            frame_str += "\n"

        self.stdscr.addstr(0, 0, frame_str)
        self.stdscr.refresh()

    def clean(self):
        self.stdscr.clear()
