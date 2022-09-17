package org.broker.application.account.handler

import com.trendyol.kediatr.NotificationHandler
import io.quarkus.runtime.Startup
import org.broker.application.account.ports.input.AccountCreatedNotification
import org.broker.application.account.ports.output.InvestorAccountEventEmitter
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
    val investorAccountEventEmitter: InvestorAccountEventEmitter
) : NotificationHandler<AccountCreatedNotification> {

    override fun handle(notification: AccountCreatedNotification) {
        Thread.sleep(Random.nextLong(1000, 10_000))
        val (accountCreated) = notification
        if ((accountCreated.cpf.substring(0, 1).toInt() % 2) == 0) {
            investorAccountEventEmitter.emitAccountApproved(accountCreated.accountId)
        } else {
            investorAccountEventEmitter.emitAccountRejected(accountCreated.accountId)
        }
    }
}