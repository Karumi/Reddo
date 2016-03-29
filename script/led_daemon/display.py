from rgbmatrix import RGBMatrix


class Display:
    def __init__(self):
        self.matrix = RGBMatrix(16)
        self.matrix.brightness = 30
        self.canvas = self.matrix.CreateFrameCanvas()
        self.width = 32
        self.height = 16

    def render(self, frame):
        print frame
        min_height = min(self.height, frame.height)
        min_width = min(self.width, frame.width)
        for row in range(min_height):
            for col in range(min_width):
                pixel = frame.get_pixel(col, row)
                r, g, b = pixel.red, pixel.green, pixel.blue
                self.canvas.SetPixel(col, row, r, g, b)
        self.canvas = self.matrix.SwapOnVSync(self.canvas)

    def clean(self):
        self.canvas.Fill(0, 0, 0)
        self.canvas = self.matrix.SwapOnVSync(self.canvas)
