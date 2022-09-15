package org.broker.domain.investor.exception

import org.broker.domain.investor.vo.Cpf

class CpfDuplicatedException(cpf: Cpf): Exception("Account already exists with the cpf $cpf!")