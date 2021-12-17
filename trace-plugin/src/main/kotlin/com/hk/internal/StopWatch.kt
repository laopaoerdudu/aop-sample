package com.hk.internal

import java.util.concurrent.TimeUnit

class StopWatch {
    private var startTime: Long = 0
    private var endTime: Long = 0
    private var elapsedTime: Long = 0

    fun start() {
        reset()
        startTime = System.nanoTime()
    }

    fun stop() {
        if (startTime != 0L) {
            endTime = System.nanoTime()
            elapsedTime = endTime - startTime
        } else {
            reset()
        }
    }

    fun getTotalTimeMillis(): Long {
        return if (elapsedTime != 0L) TimeUnit.NANOSECONDS.toMillis(endTime - startTime) else 0
    }

    private fun reset() {
        startTime = 0
        endTime = 0
        elapsedTime = 0
    }
}