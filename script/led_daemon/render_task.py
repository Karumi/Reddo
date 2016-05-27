import time


# A command to render a scrolling frame in the specified display.
class RenderTask:
    def __init__(self, fps, frame):
        self.fps = fps
        self.frame = frame

    def execute(self, display):
        windows_count = max(1, self.frame.width - display.width)
        for window in range(windows_count + 1):
            render_frame_start_time_ms = self.now_ms()
            subframe = self.frame.get_subframe(window, display.width)
            display.render(subframe)
            self.sleep_until_next_frame(render_frame_start_time_ms)
        display.clean()
        self.frame = None

    # This method makes the thread sleeps until the allocated time for each
    # frame is consumed.
    #  ________________________________
    # | Allocated frame time (1 / fps) |
    # |--------------------------------|
    # |    Render time   |  Sleep time |
    # |------------------|-------------|
    def sleep_until_next_frame(self, start_time_ms):
        allocated_frame_time_ms = 1000 / float(self.fps)
        frame_render_time_ms = self.now_ms() - start_time_ms
        sleep_time_ms = max(0, allocated_frame_time_ms - frame_render_time_ms)
        time.sleep(sleep_time_ms / 1000.0)

    def now_ms(self):
        return time.time() * 1000


class RenderTaskReader:
    def __init__(self, frame_reader):
        self.frame_reader = frame_reader

    def read(self, raw_render_task):
        fps = raw_render_task["fps"]
        frame = self.frame_reader.read(raw_render_task["frame"])
        return RenderTask(fps, frame)
