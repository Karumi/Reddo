# A command to render a scrolling frame in the specified display.
class RenderScrollingFrameTask:
    def __init__(self, renderer, fps, frame):
        self.renderer = renderer
        self.fps = fps
        self.frame = frame

    def execute(self, display):
        windows_count = max(1, self.frame.width - display.width)
        frames = [self.frame.get_subframe(window, display.width)
                  for window in range(windows_count + 1)]

        self.renderer.render(display, frames, self.fps)
        display.clean()
        self.frame = None
