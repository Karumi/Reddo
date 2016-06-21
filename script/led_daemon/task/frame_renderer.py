import time


# Renders a list of frames at a specified speed
class FrameRenderer:
    def render(self, display, frames, fps):
        for frame in frames:
            render_frame_start_time_ms = self.now_ms()
            display.render(frame)
            self.sleep_until_next_frame(render_frame_start_time_ms, fps)

    # This method makes the thread sleeps until the allocated time for each
    # frame is consumed.
    #  ________________________________
    # | Allocated frame time (1 / fps) |
    # |--------------------------------|
    # |    Render time   |  Sleep time |
    # |------------------|-------------|
    def sleep_until_next_frame(self, start_time_ms, fps):
        allocated_frame_time_ms = 1000.0 / float(fps)
        frame_render_time_ms = self.now_ms() - start_time_ms
        sleep_time_ms = max(0, allocated_frame_time_ms - frame_render_time_ms)
        time.sleep(sleep_time_ms / 1000.0)

    def now_ms(self):
        return time.time() * 1000.0
