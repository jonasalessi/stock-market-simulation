package org.broker.infra.fakedb.order

import org.broker.application.order.ports.output.ShareGateway
import org.shared.domain.entity.Share
import org.shared.domain.vo.ShareId
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ShareGatewayMem : ShareGateway {
    val data = mutableMapOf<ShareId, Share>()

    override fun findById(shareId: ShareId): Share? = data[shareId]

    fun save(share: Share) {
        data[share.id] = share
    }
}