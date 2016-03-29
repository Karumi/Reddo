import Queue
import threading
import display


class Scheduler:
    def __init__(self):
        self.display = display.Display()
        self.queue = Queue.Queue()
        self.queue_lock = threading.Lock()
        self.is_processing_task = False

    def schedule(self, task):
        self.queue.put(SchedulerTask(self, task, self.display))
        self.process_queue()

    def process_queue(self):
        with self.queue_lock:
            should_process_task = (not self.is_processing_task and
                                   not self.queue.empty())
            if should_process_task:
                self.is_processing_task = True

        if should_process_task:
            task = self.queue.get()
            thread = threading.Thread(target=task.execute)
            thread.start()


class SchedulerTask:
    def __init__(self, scheduler, inner_task, display):
        self.scheduler = scheduler
        self.inner_task = inner_task
        self.inner_task.display = display

    def execute(self):
        self.inner_task.execute()
        self.scheduler.process_queue()
