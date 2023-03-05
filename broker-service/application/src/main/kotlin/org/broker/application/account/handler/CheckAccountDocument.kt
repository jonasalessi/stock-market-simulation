package org.broker.application.account.handler

import com.trendyol.kediatr.NotificationHandler
import io.quarkus.runtime.Startup
import org.broker.application.account.ports.input.AccountCreatedEvent
import org.broker.application.account.ports.output.AccountAnalysisPublisher
import org.shared.domain.event.account.AccountApproved
import org.shared.domain.event.account.AccountRejected
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
    private val accountAnalysis: AccountAnalysisPublisher
) : NotificationHandler<AccountCreatedEvent> {

    override fun handle(notification: AccountCreatedEvent) {
        Thread.sleep(Random.nextLong(1000, 10_000))
        val (accountCreated) = notification
        if ((accountCreated.cpf.substring(0, 1).toInt() % 2) == 0) {
            accountAnalysis.pub(AccountApproved(accountCreated.accountId))
        } else {
            accountAnalysis.pub(AccountRejected(accountCreated.accountId))
        }
    }
}