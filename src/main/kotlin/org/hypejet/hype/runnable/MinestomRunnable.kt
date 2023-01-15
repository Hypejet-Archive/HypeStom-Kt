package org.hypejet.hype.runnable

import net.minestom.server.MinecraftServer
import net.minestom.server.timer.ExecutionType
import net.minestom.server.timer.Task
import net.minestom.server.timer.TaskSchedule
import java.time.Duration

abstract class MinestomRunnable : Runnable {
    private var task: Task? = null
    private var delay = TaskSchedule.immediate()
    private var repeat = TaskSchedule.stop()
    private var executionType = ExecutionType.SYNC

    constructor()
    constructor(delay: TaskSchedule, repeat: TaskSchedule) {
        this.delay = delay
        this.repeat = repeat
    }

    fun delay(delay: Duration) {
        if (delay === Duration.ZERO) return
        this.delay = TaskSchedule.duration(delay)
    }

    fun delay(delay: TaskSchedule) {
        this.delay = delay
    }

    fun repeat(repeat: Duration) {
        if (repeat === Duration.ZERO) return
        this.repeat = TaskSchedule.duration(repeat)
    }

    fun repeat(repeat: TaskSchedule) {
        this.repeat = repeat
    }

    fun type(type: ExecutionType) {
        executionType = type
    }

    fun schedule(): Task {
        val manager = MinecraftServer.getSchedulerManager()
        task = manager.scheduleTask(this, delay, repeat, executionType)
        return task as Task
    }

    fun cancel() {
        if (task != null) task!!.cancel()
    }
}
