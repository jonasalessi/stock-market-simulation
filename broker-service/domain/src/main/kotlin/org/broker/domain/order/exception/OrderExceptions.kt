package org.broker.domain.order.exception

import org.shared.domain.exception.DomainException
import java.math.BigDecimal

class OrderMinimumException(message: String) : DomainException(message)

class InsufficientShareBalanceException(ordered: Int, current: Int) :
    DomainException("You are trying to sell $ordered shares but you have $current")

class MarketClosedException : DomainException("The market works only between 10am and 5pm")

class InsufficientBalanceException(amountNeed: BigDecimal) :
    DomainException("It's necessary minimum R$ $amountNeed in you account balance")