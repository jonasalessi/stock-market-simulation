package org.broker.application.account.handler

import org.broker.application.account.ports.input.AccountCreatedNotification
import org.broker.application.investor.usecase.AccountEventEmitterMem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.shared.domain.event.AccountApproved
import org.shared.domain.event.AccountCreated
import org.shared.domain.event.AccountRejected
import org.shared.domain.vo.AccountId
import java.util.*

class CheckAccountDocumentTest {
    private lateinit var investorAccountEventEmitter: AccountEventEmitterMem

    @BeforeEach
    fun setup() {
        investorAccountEventEmitter = AccountEventEmitterMem()
    }

    @Test
    fun `should emit the event AccountApproved when CPF starts with even`() {
        val handler = CheckAccountDocument(investorAccountEventEmitter)
        handler.handle(accountCreated(AccountId(UUID.randomUUID()), "200.000.000-00"))
        Assertions.assertInstanceOf(AccountApproved::class.java, investorAccountEventEmitter.events.first())
    }

    @Test
    fun `should emit the event AccountRejected when CPF starts with odd`() {
        val handler = CheckAccountDocument(investorAccountEventEmitter)
        handler.handle(accountCreated(AccountId(UUID.randomUUID()), "300.000.000-00"))
        Assertions.assertInstanceOf(AccountRejected::class.java, investorAccountEventEmitter.events.first())
    }

    private fun accountCreated(accountId: AccountId, cpf: String) = AccountCreatedNotification(
        AccountCreated(accountId, cpf)
    )
}