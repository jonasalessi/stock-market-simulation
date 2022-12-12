package org.broker.application.order.ports.output

import org.shared.domain.entity.Share
import org.shared.domain.vo.ShareId

interface ShareGateway {
    fun findById(shareId: ShareId): Share?
}