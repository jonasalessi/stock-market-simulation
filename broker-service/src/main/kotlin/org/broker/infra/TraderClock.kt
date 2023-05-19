package org.broker.infra

import jakarta.inject.Singleton
import org.broker.application.order.ports.output.TraderClock
import java.time.ZonedDateTime

@Singleton
class TraderClock: TraderClock {

    override fun now(): ZonedDateTime = ZonedDateTime.now()
}