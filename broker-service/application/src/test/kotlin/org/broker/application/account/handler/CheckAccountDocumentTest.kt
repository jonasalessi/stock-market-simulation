package org.broker.application.account.handler

import org.broker.application.investor.usecase.InvestorAccountEventEmitterMem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.shared.domain.event.AccountApproved
import org.shared.domain.event.AccountCreated
import org.shared.domain.event.AccountRejected
import org.shared.domain.vo.AccountId
import java.util.*

class CheckAccountDocumentTest {
    private lateinit var investorAccountEventEmitter: InvestorAccountEventEmitterMem

    @BeforeEach
    fun setup() {
        investorAccountEventEmitter = InvestorAccountEventEmitterMem()
    }

    @Test
    fun `should emit the event AccountApproved when CPF starts with even`() {
        val handler = CheckAccountDocument(investorAccountEventEmitter)
        handler.execute(AccountCreated(AccountId(UUID.randomUUID()), "200.000.000-00"))
        Assertions.assertInstanceOf(AccountApproved::class.java, investorAccountEventEmitter.events[0])
    }

    @Test
    fun `should emit the event AccountRejected when CPF starts with odd`() {
        val handler = CheckAccountDocument(investorAccountEventEmitter)
        handler.execute(AccountCreated(AccountId(UUID.randomUUID()), "300.000.000-00"))
        Assertions.assertInstanceOf(AccountRejected::class.java, investorAccountEventEmitter.events[0])
    }
}