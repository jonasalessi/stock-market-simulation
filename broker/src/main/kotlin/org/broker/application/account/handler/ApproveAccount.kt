package org.broker.application.account.handler

import com.trendyol.kediatr.NotificationHandler
import org.broker.application.account.ports.input.AccountApprovedEvent
import org.broker.application.account.ports.output.AccountRepository
import org.broker.domain.account.exception.AccountNotFoundException
import org.springframework.stereotype.Component

@Component
class ApproveAccount(
    private val accountRepository: AccountRepository
) : NotificationHandler<AccountApprovedEvent> {

    override suspend fun handle(notification: AccountApprovedEvent) {
        val accountId = notification.event.id
        val account = accountRepository.findById(accountId) ?: throw AccountNotFoundException(accountId)
        account.approve()
    }
}