package org.broker.domain.account.exception

import org.broker.domain.account.vo.Cpf
import org.shared.domain.exception.DomainException
import org.shared.domain.vo.AccountId
import org.shared.domain.vo.ShareId


class AccountFlowStatusException(message: String) : DomainException(message)

class AccountNotFoundException(id: AccountId): DomainException("Account id ${id.uuid} not found!")

class CpfDuplicatedException(cpf: Cpf): DomainException("Account already exists with the cpf $cpf!")

class CpfInvalidException(cpf: String?) : DomainException("CPF $cpf is invalid!")

class Under18YearsOldException: DomainException("Under 18 years old are not allowed to open an account!")

class AccountShareNotFoundException(shareId: ShareId): DomainException("You don't have shares of ${shareId.symbol}!")