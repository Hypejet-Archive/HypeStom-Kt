package xyz.citywide.next.runnable;

import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.ExecutionType;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public abstract class MinestomRunnable implements Runnable {
    private Task task;
    private TaskSchedule delay = TaskSchedule.immediate();
    private TaskSchedule repeat = TaskSchedule.stop();
    private ExecutionType executionType = ExecutionType.SYNC;

    public MinestomRunnable() {}
    public MinestomRunnable(TaskSchedule delay, TaskSchedule repeat) {
        this.delay = delay;
        this.repeat = repeat;
    }

    public void delay(@NotNull Duration delay) {
        if(delay == Duration.ZERO) return;
        this.delay = TaskSchedule.duration(delay);
    }

    public void delay(@NotNull TaskSchedule delay) {
        this.delay = delay;
    }

    public void repeat(@NotNull Duration repeat) {
        if(repeat == Duration.ZERO) return;
        this.repeat = TaskSchedule.duration(repeat);
    }

    public void repeat(@NotNull TaskSchedule repeat) {
        this.repeat = repeat;
    }

    public void type(@NotNull ExecutionType type) {
        this.executionType = type;
    }

    public Task schedule() {
        var manager = MinecraftServer.getSchedulerManager();
        this.task = manager.scheduleTask(this, delay, repeat, executionType);
        return task;
    }

    public void cancel() {
        if(task != null)
            this.task.cancel();
    }
}
