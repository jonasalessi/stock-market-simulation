package org.broker.domain.order.exception

class MarketClosedException: Exception("The market works only between 10am and 5pm")