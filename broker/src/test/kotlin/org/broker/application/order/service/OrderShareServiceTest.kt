package org.broker.application.order.service

import org.broker.infra.fakedb.order.OrderRepositoryMem
import org.broker.infra.fakedb.order.ShareGatewayMem
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.shared.domain.exception.ShareNotFoundException
import org.shared.domain.vo.ShareId


class OrderShareServiceTest {
    private lateinit var orderShareService: OrderShareService

    @BeforeEach
    fun setup() {
        orderShareService = OrderShareService(ShareGatewayMem(), OrderRepositoryMem())
    }

    @Test
    fun `should return an exception ShareNotFoundException when the XX does not exists`() {
        val ex = assertThrows<ShareNotFoundException> {
            orderShareService.findShareById(ShareId("XX"))
        }
        assertThat(ex.message, equalTo("Share XX not found!"))
    }
}