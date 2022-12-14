package org.broker.application.account.handler

import com.trendyol.kediatr.NotificationHandler
import io.quarkus.runtime.Startup
import org.broker.application.account.ports.input.AccountRejectedNotification
import org.broker.domain.account.exception.AccountNotFoundException
import org.broker.application.account.ports.output.AccountRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@Startup
class RejectAccount(
    private val accountRepository: AccountRepository
) : NotificationHandler<AccountRejectedNotification> {

    override fun handle(notification: AccountRejectedNotification) {
        val accountId = notification.event.id
        val account = accountRepository.findById(accountId) ?: throw AccountNotFoundException(accountId)
        account.reject()
    }
}