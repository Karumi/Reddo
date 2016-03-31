#!/usr/bin/python

import BaseHTTPServer
import time
from led_daemon.router import Router
from led_daemon.server import Server


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
    main()
