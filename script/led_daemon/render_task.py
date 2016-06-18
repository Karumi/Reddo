import time


# A command to render a scrolling frame in the specified display.
class RenderScrollingFrameTask:
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


class RenderVideoTask:
    def __init__(self, fps, frames):
        self.fps = fps
        self.frames = frames

    def execute(self, display):
        for frame in frames:
            render_frame_start_time_ms = self.now_ms()
            display.render(frame)
            self.sleep_until_next_frame(render_frame_start_time_ms)
        display.clean()
        self.frames = None

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


class RenderTaskFactory:
    def create(self, render_type, fps, frames):
        if render_type == "video":
            return RenderVideoTask(fps, frames)
        elif render_type == "scrolling":
            return RenderScrollingFrameTask(fps, frames[0])
        else:
            raise ValueError("Unknown render type received: " + render_type)


class RenderTaskReader:
    def __init__(self, frame_reader, render_task_factory):
        self.frame_reader = frame_reader
        self.render_task_factory = render_task_factory

    def read(self, raw_render_task):
        render_type = raw_render_task["type"]
        fps = raw_render_task["fps"]
        frames = [self.frame_reader.read(frame)
                  for frame in raw_render_task["frames"]]
        return self.render_task_factory.create(render_type, fps, frames)
