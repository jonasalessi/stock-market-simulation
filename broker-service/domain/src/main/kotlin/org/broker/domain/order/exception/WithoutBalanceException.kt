package org.broker.domain.order.exception

import java.math.BigDecimal

class WithoutBalanceException(amountNeed: BigDecimal): Exception("It's necessary minimum R$ $amountNeed in you account balance")