package org.broker.domain.account.exception

class CpfInvalidException(cpf: String?) : Exception("CPF $cpf is invalid!")