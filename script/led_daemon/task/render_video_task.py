# A command to render a video frame by frame in the specified display
class RenderVideoTask:
    def __init__(self, renderer, fps, frames):
        self.renderer = renderer
        self.fps = fps
        self.frames = frames

    def execute(self, display):
        self.renderer.render(display, self.frames, self.fps)
        display.clean()
        self.frames = None
