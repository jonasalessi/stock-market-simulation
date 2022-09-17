package org.broker.application.account.ports.input

import com.trendyol.kediatr.Command
import java.time.LocalDate

data class CreateInvestorAccountCommand(
    val name: String,
    val birthday: LocalDate,
    val country: String,
    val city: String,
    val cpf: String
) : Command
