package org.broker.application.account.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.account.ports.input.CreateAccountCommand
import org.broker.application.account.ports.output.AccountEventEmitter
import org.broker.domain.account.entity.Account
import org.broker.domain.account.exception.CpfDuplicatedException
import org.broker.application.account.ports.output.AccountRepository
import org.broker.domain.account.vo.Cpf
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@Startup
class CreateAccount(
    val accountEventEmitter: AccountEventEmitter,
    val repository: AccountRepository
) : CommandHandler<CreateAccountCommand> {

    override fun handle(command: CreateAccountCommand) {
        val cpf = Cpf(command.cpf)
        if (repository.existsByCpf(cpf)) {
            throw CpfDuplicatedException(cpf)
        }
        val account = Account.create(
            name = command.name,
            birthday = command.birthday,
            city = command.city,
            country = command.country,
            cpf = cpf
        )
        repository.save(account)
        accountEventEmitter.emitAccountCreated(account.id, account.investor.cpf.digits)
    }
}