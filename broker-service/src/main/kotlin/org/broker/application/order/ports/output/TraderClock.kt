package org.broker.application.order.ports.output

import java.time.ZonedDateTime

interface TraderClock {
    fun now(): ZonedDateTime
}