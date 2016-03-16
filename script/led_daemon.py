import socket
import struct
import time
from rgbmatrix import RGBMatrix

class ReddoSocket:
	def __init__(self, port):
		self.buffer = ""
		self.connection = None
		self.initialized = False
		self.port = port
		self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.timeout = 10

	def accept_connection(self):
		if not self.initialized:
			self.initialize_socket()

		if self.connection:
			self.connection.close()

		connection, address = self.socket.accept()
		self.connection = connection

	def read_data(self, bytes_count):
		starting_time = time.time()
		while len(self.buffer) < bytes_count:
			if time.time() - starting_time > self.timeout:
				self.buffer = ""
				return None

			data = self.connection.recv(1024)
			self.buffer = self.buffer + data
		data = self.buffer[:bytes_count]
		self.buffer = self.buffer[bytes_count:]
		return data

	# Private
	def initialize_socket(self):
		self.socket.bind(("localhost", self.port))
		self.socket.listen(1)
		self.initialized = True

class Pixel:
	def __init__(self, red, green, blue):
		self.red = red
		self.green = green
		self.blue = blue

	def __repr__(self):
		return "(r: " + str(self.red) + ", g: " + str(self.green) + ", b: " + str(self.blue) + ")"

class Frame:
	def __init__(self, width, height, data):
		self.width = width
		self.height = height
		self.data = data

	def get_pixel(self, x, y):
		if x < 0 or x >= self.width or y < 0 or y >= self.height:
			return None

		return self.data[y * self.width + x]

	def print_raw(self):
		print "[" + str(self.width) + " x " + str(self.height) + "]"
		print self.data

	@staticmethod
	def from_connection(reddo_socket):
		raw_height = reddo_socket.read_data(4)
		if not raw_height:
			return None

		raw_width = reddo_socket.read_data(4)
		if not raw_width:
			return None

		height = int(struct.unpack("!I", raw_height)[0])
		width = int(struct.unpack("!I", raw_width)[0])

		raw_data = reddo_socket.read_data(4 * width * height)
		if not raw_data:
			return None

		data = [Pixel(ord(raw_data[i + 1]), ord(raw_data[i + 2]), ord(raw_data[i + 3])) for i in range(0, len(raw_data), 4)]
		return Frame(width, height, data)

class Daemon:
	def __init__(self, port):
		self.matrix = RGBMatrix(16)
		self.port = port
		self.reddo_socket = ReddoSocket(9001)
		self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

	def start(self):
		self.matrix.brightness = 30
		canvas = self.matrix.CreateFrameCanvas()
		while True:
			self.reddo_socket.accept_connection()
			frame = Frame.from_connection(self.reddo_socket)
			while frame:
				for i in range(frame.height):
					for j in range(frame.width):
						pixel = frame.get_pixel(j, i)
						canvas.SetPixel(j, i, pixel.red, pixel.green, pixel.blue)

				canvas = self.matrix.SwapOnVSync(canvas)
				frame = Frame.from_connection(self.reddo_socket)
			canvas.Fill(0, 0, 0)
			canvas = self.matrix.SwapOnVSync(canvas)

def main():
	daemon = Daemon(9001)
	daemon.start()

if __name__ == "__main__":
	main()