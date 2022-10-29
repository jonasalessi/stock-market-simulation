package org.broker.application.order.stub

import org.broker.application.order.ports.output.TraderClock
import java.time.ZonedDateTime

class TraderClockStub(private val date: ZonedDateTime): TraderClock {
    override fun now() = date
}