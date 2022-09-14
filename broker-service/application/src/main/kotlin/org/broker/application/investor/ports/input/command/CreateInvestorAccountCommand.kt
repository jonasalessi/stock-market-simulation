package org.broker.application.investor.ports.input.command

import com.trendyol.kediatr.Command

data class CreateInvestorAccountCommand(
val cpf: String
): Command
