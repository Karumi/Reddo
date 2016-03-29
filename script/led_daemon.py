#!/usr/bin/python
# -*- coding: utf-8 -*-

import BaseHTTPServer
import time
from led_daemon import router


router = router.Router()


class Server(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_POST(self):
        router.route(self)
        self.end_headers()


def main():
    httpd = BaseHTTPServer.HTTPServer(('', 9050), Server)
    print time.asctime(), "Server Starts"
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    httpd.server_close()
    print time.asctime(), "Server Stops"

if __name__ == "__main__":
    main()
