package org.broker.application.account.handler

import com.trendyol.kediatr.NotificationHandler
import org.broker.application.account.ports.input.AccountRejectedEvent
import org.broker.application.account.ports.output.AccountRepository
import org.broker.domain.account.exception.AccountNotFoundException
import org.springframework.stereotype.Component

@Component
class RejectAccount(
    private val accountRepository: AccountRepository
) : NotificationHandler<AccountRejectedEvent> {

    override suspend fun handle(notification: AccountRejectedEvent) {
        val accountId = notification.event.id
        val account = accountRepository.findById(accountId) ?: throw AccountNotFoundException(accountId)
        account.reject()
    }
}