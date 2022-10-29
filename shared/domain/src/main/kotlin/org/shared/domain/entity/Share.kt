package org.shared.domain.entity

import org.shared.domain.vo.ShareId

class Share(override val id: ShareId, val category: ShareCategory) : Entity<ShareId>() {
    companion object {
        val EMPTY = Share(id = ShareId(""), category = ShareCategory.FRACTIONAL)
    }
}

enum class ShareCategory {
    FRACTIONAL,
    INTEGRAL
}
