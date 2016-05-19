#!/usr/bin/python

import BaseHTTPServer
import logging.handlers
import sys
import time

from led_daemon.server import Server

LOG_FILENAME = 'reddo_error_logger.txt'

# Set up a specific logger with our desired output level
main_logger = logging.getLogger(__file__)
main_logger.setLevel(logging.DEBUG)

# Add the log message handler to the logger
handler = logging.handlers.RotatingFileHandler(
              LOG_FILENAME, maxBytes=2000, backupCount=5)

main_logger.addHandler(handler)


def main():
    httpd = BaseHTTPServer.HTTPServer(('', 9050), Server)
    print time.asctime(), "Server Starts"
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    httpd.server_close()
    print time.asctime(), "Server Stops"
    exit()

if __name__ == "__main__":
    try:
        main()
    except:
        main_logger.debug("Error %s", sys.exc_info()[0])
