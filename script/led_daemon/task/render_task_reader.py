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
