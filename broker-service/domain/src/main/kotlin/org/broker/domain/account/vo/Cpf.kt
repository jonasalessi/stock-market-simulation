package org.broker.domain.account.vo

import org.broker.domain.account.exception.CpfInvalidException

data class Cpf(
    val digits: String
) {
    init {
        if (!this.isValidCPF()) {
            throw CpfInvalidException(digits)
        }
    }

    private fun isValidCPF(): Boolean {
        val cpf = cleanUp()
        if (cpf.length != CPF_SIZE) return false
        val nineDigits = cpf.toList().map { it.digitToInt() }.slice(0..8)
        val calculatedFirstDigit = this.calculateDigit(nineDigits, 10)
        val calculatedSecondDigit = this.calculateDigit(nineDigits.plus(calculatedFirstDigit), 11)
        val cpfRecalculated = nineDigits.joinToString(separator = "") + calculatedFirstDigit + calculatedSecondDigit
        return cpfRecalculated == cpf
    }

    private fun cleanUp() = digits.replace("[^\\d]".toRegex(), "")


    private fun calculateDigit(cpfDigits: List<Int>, factor: Int): Int {
        val secondRest = cpfDigits
            .mapIndexed { idx, value -> value * (factor - idx) }
            .sum() % CPF_SIZE
        return this.calculateRestDigit(secondRest)
    }

    private fun calculateRestDigit(cpfDigits: Int) =
        if (cpfDigits < 2) 0 else CPF_SIZE - cpfDigits

    override fun toString() = digits

    companion object {
        const val CPF_SIZE = 11
    }
}