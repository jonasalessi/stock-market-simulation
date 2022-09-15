package org.broker.application.account.handler

import org.broker.application.investor.ports.output.InvestorAccountEventEmitter
import org.shared.domain.event.AccountCreated
import javax.enterprise.context.ApplicationScoped
import kotlin.random.Random

/**
 * Fake process that is simulating a delay for checking the documents.
 * When Account CPF starts with even it will create the event AccountApproved
 * Otherwise will create the event AccountRejected
 */
@ApplicationScoped
class CheckAccountDocument(
    val investorAccountEventEmitter: InvestorAccountEventEmitter
) {

    fun execute(accountCreated: AccountCreated) {
        Thread.sleep(Random.nextLong(1000, 10_000))

        if ((accountCreated.cpf.substring(0,1).toInt() % 2) == 0) {
            investorAccountEventEmitter.emitAccountApproved(accountCreated.accountId)
        } else {
            investorAccountEventEmitter.emitAccountRejected(accountCreated.accountId)
        }
    }
}