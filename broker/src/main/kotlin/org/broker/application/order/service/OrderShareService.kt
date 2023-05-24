package org.broker.application.order.service

import org.broker.application.order.ports.output.OrderRepository
import org.broker.application.order.ports.output.ShareGateway
import org.broker.domain.order.entity.Order
import org.shared.domain.entity.Share
import org.shared.domain.exception.ShareNotFoundException
import org.shared.domain.vo.ShareId
import org.springframework.stereotype.Service

@Service
class OrderShareService(
    private val shareGateway: ShareGateway,
    private val orderRepository: OrderRepository,
) {

    fun findShareById(shareId: ShareId): Share =
        shareGateway.findById(shareId) ?: throw ShareNotFoundException(shareId)

    fun saveOrder(order: Order) {
        orderRepository.save(order)
    }
}