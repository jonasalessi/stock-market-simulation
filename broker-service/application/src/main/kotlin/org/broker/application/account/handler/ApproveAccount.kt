package org.broker.application.account.handler

import com.trendyol.kediatr.NotificationHandler
import io.quarkus.runtime.Startup
import org.broker.application.account.ports.input.AccountApprovedNotification
import org.broker.domain.account.exception.AccountNotFoundException
import org.broker.application.account.ports.output.AccountRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@Startup
class ApproveAccount(
    private val accountRepository: AccountRepository
) : NotificationHandler<AccountApprovedNotification> {

    override fun handle(notification: AccountApprovedNotification) {
        val accountId = notification.event.id
        val account = accountRepository.findById(accountId) ?: throw AccountNotFoundException(accountId)
        account.approve()
    }
}