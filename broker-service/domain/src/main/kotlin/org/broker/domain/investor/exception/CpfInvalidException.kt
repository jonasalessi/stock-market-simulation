package org.broker.domain.investor.exception

class CpfInvalidException(cpf: String?) : Exception("CPF $cpf is invalid!")