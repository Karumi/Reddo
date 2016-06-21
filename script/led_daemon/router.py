import json
from frame import FrameReader
from task.frame_renderer import FrameRenderer
from task.render_task_factory import RenderTaskFactory
from task.render_task_reader import RenderTaskReader
from scheduler import Scheduler


class Router:
    def __init__(self):
        frame_reader = FrameReader()
        renderer = FrameRenderer()
        render_task_factory = RenderTaskFactory(renderer)
        self.reader = RenderTaskReader(frame_reader, render_task_factory)
        self.scheduler = Scheduler()

    def route(self, handler):
        if self.is_render_request(handler):
            self.route_render_request(handler)
        else:
            print("Unknown request: " + handler.path)

    def is_render_request(self, handler):
        return handler.path == "/render" and handler.command == 'POST'

    def route_render_request(self, handler):
        content_len = int(handler.headers.getheader('content-length', 0))
        json_contents = json.loads(handler.rfile.read(content_len))
        task = self.reader.read(json_contents)
        self.scheduler.schedule(task)
        handler.send_response(200)
