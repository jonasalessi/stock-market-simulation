package org.broker.domain.account.vo

enum class AccountStatus {
    WAITING_BROKER_CHECKING,
    WAITING_DEPOSIT,
    ACTIVE,
    REJECTED
}