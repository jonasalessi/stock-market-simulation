package org.broker.domain.investor.vo

import org.broker.domain.investor.exception.CpfInvalidException
import org.broker.domain.investor.vo.Cpf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CpfTest {

    @ParameterizedTest
    @MethodSource("validCpf")
    fun `should accept valid CPF`(cpf: String) {
        val cpfCreated = Cpf(cpf)
        assertEquals(cpf, cpfCreated.digits)
    }

    @Test
    fun `Should not accept an empty invalid CPF`() {
        val exception = assertThrows<CpfInvalidException> { Cpf("") }
        assertInstanceOf(CpfInvalidException::class.java, exception)
    }

    @Test
    fun `Should not accept an invalid CPF`() {
        val exception = assertThrows<CpfInvalidException> { Cpf("192.531.148-12") }
        assertInstanceOf(CpfInvalidException::class.java, exception)
    }

    @Test
    fun `Should not accept CPF with not minimal digits`() {
        val exception = assertThrows<CpfInvalidException> { Cpf("123") };
        assertInstanceOf(CpfInvalidException::class.java, exception)
    }

    @Test
    fun `Should not accept CPF greather than minimal digits`() {
        val exception = assertThrows<CpfInvalidException> { Cpf("122424234242423243312345612345678") }
        assertInstanceOf(CpfInvalidException::class.java, exception)
    }

    companion object {
        @JvmStatic
        fun validCpf() = Stream.of(
            "787.101.780-23",
            "386.005.508-90",
            "397.974.888-02",
            "192 531.148-19"
        )
    }
}