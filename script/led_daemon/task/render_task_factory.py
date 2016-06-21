from render_scrolling_frame_task import RenderScrollingFrameTask
from render_video_task import RenderVideoTask


class RenderTaskFactory:
    def __init__(self, renderer):
        self.renderer = renderer

    def create(self, render_type, fps, frames):
        if render_type == "video":
            return RenderVideoTask(renderer, fps, frames)
        elif render_type == "scroll":
            return RenderScrollingFrameTask(renderer, fps, frames[0])
        else:
            raise ValueError("Unknown render type received: " + render_type)
