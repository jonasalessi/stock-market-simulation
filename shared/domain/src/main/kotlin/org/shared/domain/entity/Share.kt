package org.shared.domain.entity

import java.math.BigDecimal

class Share(override val id: String, val price: BigDecimal, val category: ShareCategory) : Entity<String>()

enum class ShareCategory{
    FRACTIONAL,
    INTEGRAL
}