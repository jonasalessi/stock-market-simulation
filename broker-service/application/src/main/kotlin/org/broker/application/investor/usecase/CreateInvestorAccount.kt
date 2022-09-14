package org.broker.application.investor.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.investor.ports.input.command.CreateInvestorAccountCommand
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@Startup
class CreateInvestorAccount : CommandHandler<CreateInvestorAccountCommand> {

    override fun handle(command: CreateInvestorAccountCommand) {
        TODO("Not yet implemented")
    }
}