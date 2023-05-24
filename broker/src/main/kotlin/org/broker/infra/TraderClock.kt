package org.broker.infra

import org.broker.application.order.ports.output.TraderClock
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class TraderClock: TraderClock {

    override fun now(): ZonedDateTime = ZonedDateTime.now()
}