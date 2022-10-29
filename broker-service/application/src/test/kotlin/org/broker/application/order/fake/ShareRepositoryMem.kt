package org.broker.application.order.fake

import org.broker.application.order.ports.output.ShareRepository
import org.shared.domain.entity.Share
import org.shared.domain.vo.ShareId

class ShareRepositoryMem : ShareRepository {
    val data = mutableMapOf<ShareId, Share>()

    override fun findById(shareId: ShareId): Share? = data[shareId]

    fun save(share: Share) {
        data[share.id] = share
    }
}