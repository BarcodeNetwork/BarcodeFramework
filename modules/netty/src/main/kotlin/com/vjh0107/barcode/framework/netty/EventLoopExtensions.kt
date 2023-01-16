package com.vjh0107.barcode.framework.netty

import io.netty.channel.EventLoop
import java.util.concurrent.TimeUnit

/**
 * kotlin dsl shortcut
 */
fun EventLoop.schedule(delay: Long, unit: TimeUnit, command: () -> Unit) {
    schedule(command, delay, unit)
}