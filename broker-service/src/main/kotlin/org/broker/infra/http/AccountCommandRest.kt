package org.broker.infra.http

import com.trendyol.kediatr.Mediator
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.broker.application.account.ports.input.CreateAccountCommand


@Path("/accounts")
class AccountCommandRest(
    private val mediator: Mediator
) {

    @POST
    suspend fun createAccount(command: CreateAccountCommand) = mediator.send(command)
}