package org.broker.domain.account.exception

import org.broker.domain.account.vo.Cpf

class CpfDuplicatedException(cpf: Cpf): Exception("Account already exists with the cpf $cpf!")