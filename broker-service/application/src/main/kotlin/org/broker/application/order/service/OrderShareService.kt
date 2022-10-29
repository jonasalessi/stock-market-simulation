package org.broker.application.order.service

import org.broker.application.order.ports.output.OrderRepository
import org.broker.application.order.ports.output.ShareRepository
import org.broker.domain.order.entity.Order
import org.shared.domain.entity.Share
import org.shared.domain.exception.ShareNotFoundException
import org.shared.domain.vo.ShareId
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OrderShareService(
    private val shareRepository: ShareRepository,
    private val orderRepository: OrderRepository,
) {

    fun findShareById(shareId: ShareId): Share =
        shareRepository.findById(shareId) ?: throw ShareNotFoundException(shareId)

    fun saveOrder(order: Order) {
        orderRepository.save(order)
    }
}