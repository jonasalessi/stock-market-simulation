package org.broker.infra.http

import com.trendyol.kediatr.Mediator
import org.broker.application.account.ports.input.CreateAccountCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/accounts")
class AccountCommandRest(
    private val mediator: Mediator
) {

    @PostMapping
    suspend fun createAccount(command: CreateAccountCommand) = mediator.send(command)
}