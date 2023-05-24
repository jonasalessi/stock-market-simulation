package org.broker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.trendyol.kediatr.spring"])
class BrokerApplication

fun main(args: Array<String>) {
    runApplication<BrokerApplication>(*args)
}
