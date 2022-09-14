package org.broker.application.investor.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.investor.ports.input.CreateInvestorAccountCommand
import org.broker.application.investor.ports.output.InvestorAccountEventEmitter
import org.broker.domain.investor.entity.Investor
import org.broker.domain.investor.exception.CpfDuplicatedException
import org.broker.domain.investor.repository.InvestorRepository
import org.broker.domain.investor.vo.Cpf
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@Startup
class CreateInvestorAccount(
    val investorAccountEventEmitter: InvestorAccountEventEmitter,
    val repository: InvestorRepository
) : CommandHandler<CreateInvestorAccountCommand> {

    override fun handle(command: CreateInvestorAccountCommand) {
        val cpf = Cpf(command.cpf)
        if (repository.existsByCpf(cpf)) {
            throw CpfDuplicatedException(cpf)
        }
        val investor = Investor.create(
            name = command.name,
            birthday = command.birthday,
            city = command.city,
            country = command.country,
            cpf = cpf
        )
        repository.save(investor)
        investorAccountEventEmitter.emitAccountCreated(investor.account.id, investor.account.cpf.digits)
    }
}