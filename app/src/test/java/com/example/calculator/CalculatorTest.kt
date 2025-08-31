package com.example.calculator

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CalculatorTest {

    private lateinit var calculator: Calculator

    @Before
    fun setup() {
        calculator = Calculator()
    }

    @Test
    fun `test basic addition`() {
        calculator.inputNumber("5")
        calculator.inputOperator("+")
        calculator.inputNumber("3")
        val result = calculator.calculate()

        assertEquals("8", result.displayValue)
    }

    @Test
    fun `test division by zero returns error`() {
        calculator.inputNumber("5")
        calculator.inputOperator("/")
        calculator.inputNumber("0")
        val result = calculator.calculate()

        assertEquals("Error", result.displayValue)
        assertTrue(result.isError)
    }

    @Test
    fun `test decimal input`() {
        calculator.inputNumber("5")
        calculator.decimal()
        calculator.inputNumber("5")
        val result = calculator.getCurrentState()

        assertEquals("5.5", result.displayValue)
    }

    @Test
    fun `test percentage calculation`() {
        calculator.inputNumber("50")
        val result = calculator.percent()

        assertEquals("0.5", result.displayValue)
    }

    @Test
    fun `test clear function`() {
        calculator.inputNumber("123")
        calculator.inputOperator("+")
        calculator.inputNumber("456")
        val result = calculator.clear()

        assertEquals("0", result.displayValue)
        assertEquals("", result.expression)
    }
}