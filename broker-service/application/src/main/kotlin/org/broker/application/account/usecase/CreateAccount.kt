package org.broker.application.account.usecase

import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import org.broker.application.account.ports.input.CreateAccountCommand
import org.broker.application.account.ports.output.AccountEventEmitter
import org.broker.application.account.ports.output.AccountRepository
import org.broker.domain.account.entity.Account
import org.broker.domain.account.exception.CpfDuplicatedException
import org.broker.domain.account.vo.Cpf
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@Startup
class CreateAccount(
    private val accountEventEmitter: AccountEventEmitter,
    private val repository: AccountRepository
) : CommandHandler<CreateAccountCommand> {

    override fun handle(command: CreateAccountCommand) {
        val cpf = Cpf(command.cpf)
        if (repository.existsByCpf(cpf)) throw CpfDuplicatedException(cpf)
        val account = Account.build(
            withName = command.name,
            withBirthday = command.birthday,
            withCity = command.city,
            withCountry = command.country,
            withCpf = cpf
        )
        repository.save(account)
        accountEventEmitter.emitAccountCreated(account.id, account.investor.cpf.digits)
    }
}