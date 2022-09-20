package org.broker.application.account.ports.input

import com.trendyol.kediatr.Notification
import org.shared.domain.event.AccountApproved
import org.shared.domain.event.AccountCreated
import org.shared.domain.event.AccountRejected

data class AccountCreatedNotification(val event: AccountCreated): Notification

data class AccountApprovedNotification(val event: AccountApproved): Notification

data class AccountRejectedNotification(val event: AccountRejected): Notification