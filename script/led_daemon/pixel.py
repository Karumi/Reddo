class Pixel:
    def __init__(self, red, green, blue):
        self.red = red
        self.green = green
        self.blue = blue

    def __repr__(self):
        red = str(self.red)
        green = str(self.green)
        blue = str(self.blue)
        return "(r: " + red + ", g: " + green + ", b: " + blue + ")"
