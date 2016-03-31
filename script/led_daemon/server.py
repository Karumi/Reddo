import BaseHTTPServer
from led_daemon.router import Router


class Server(BaseHTTPServer.BaseHTTPRequestHandler):

    ROUTER = Router()

    def do_POST(self):
        Server.ROUTER.route(self)
        self.end_headers()
