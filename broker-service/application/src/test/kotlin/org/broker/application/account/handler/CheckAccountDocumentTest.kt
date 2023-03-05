package org.broker.application.account.handler

import org.broker.application.account.fake.AccountEventPublisherMem
import org.broker.application.account.ports.input.AccountCreatedEvent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.shared.domain.event.account.AccountApproved
import org.shared.domain.event.account.AccountCreated
import org.shared.domain.event.account.AccountRejected
import org.shared.domain.vo.AccountId
import java.util.*

class CheckAccountDocumentTest {
    private lateinit var eventPublisher: AccountEventPublisherMem

    @BeforeEach
    fun setup() {
        eventPublisher = AccountEventPublisherMem()
    }

    @Test
    fun `should emit the event AccountApproved when CPF starts with even`() {
        val handler = CheckAccountDocument(eventPublisher)
        handler.handle(accountCreated(AccountId(UUID.randomUUID()), "200.000.000-00"))
        Assertions.assertInstanceOf(AccountApproved::class.java, eventPublisher.events.first())
    }

    @Test
    fun `should emit the event AccountRejected when CPF starts with odd`() {
        val handler = CheckAccountDocument(eventPublisher)
        handler.handle(accountCreated(AccountId(UUID.randomUUID()), "300.000.000-00"))
        Assertions.assertInstanceOf(AccountRejected::class.java, eventPublisher.events.first())
    }

    private fun accountCreated(accountId: AccountId, cpf: String) = AccountCreatedEvent(
        AccountCreated(accountId, cpf)
    )
}