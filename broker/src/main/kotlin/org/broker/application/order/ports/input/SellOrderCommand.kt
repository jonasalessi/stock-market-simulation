package org.broker.application.order.ports.input

import com.trendyol.kediatr.Command
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.ShareId
import java.math.BigDecimal

data class SellOrderCommand(
    val shareId: ShareId,
    val price: BigDecimal,
    val quantity: Int,
    val accountId: AccountId
) : Command