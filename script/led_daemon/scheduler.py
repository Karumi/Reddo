import Queue
from threading import Thread
from led_display import Display


# Processes all the given tasks in sequential order.
# It has a limited amount of enqueued tasks, if the limit is reached then new
# tasks will not be executed.
class Scheduler:
    def __init__(self):
        self.display = Display()
        self.queue = Queue.Queue(64)
        thread = Thread(target=self.process_queue)
        thread.daemon = True
        thread.start()

    def schedule(self, task):
        self.queue.put_nowait(task)

    def process_queue(self):
        while True:
            task = self.queue.get()
            task.execute(self.display)
            self.queue.task_done()
