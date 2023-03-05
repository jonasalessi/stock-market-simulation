package org.broker.application.account.handler

import com.trendyol.kediatr.NotificationHandler
import io.quarkus.runtime.Startup
import org.broker.application.account.ports.input.AccountCreatedEvent
import org.broker.application.account.ports.output.AccountEventPublisher
import javax.enterprise.context.ApplicationScoped
import kotlin.random.Random

/**
 * Fake process that is simulating a delay for checking the documents.
 * When Account CPF starts with even it will create the event AccountApproved
 * Otherwise will create the event AccountRejected
 */
@ApplicationScoped
@Startup
class CheckAccountDocument(
    private val eventEmitter: AccountEventPublisher
) : NotificationHandler<AccountCreatedEvent> {

    override fun handle(notification: AccountCreatedEvent) {
        Thread.sleep(Random.nextLong(1000, 10_000))
        val (accountCreated) = notification
        if ((accountCreated.cpf.substring(0, 1).toInt() % 2) == 0) {
            eventEmitter.emitAccountApproved(accountCreated.accountId)
        } else {
            eventEmitter.emitAccountRejected(accountCreated.accountId)
        }
    }
}