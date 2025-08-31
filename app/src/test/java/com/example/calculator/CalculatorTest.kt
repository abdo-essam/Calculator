package com.example.calculator

import com.example.calculator.model.CalculatorOperation
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
        calculator.inputOperation(CalculatorOperation.ADD)
        calculator.inputNumber("3")
        val result = calculator.calculate()

        assertEquals("8", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test basic subtraction`() {
        calculator.inputNumber("10")
        calculator.inputOperation(CalculatorOperation.SUBTRACT)
        calculator.inputNumber("4")
        val result = calculator.calculate()

        assertEquals("6", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test multiplication`() {
        calculator.inputNumber("7")
        calculator.inputOperation(CalculatorOperation.MULTIPLY)
        calculator.inputNumber("8")
        val result = calculator.calculate()

        assertEquals("56", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test division`() {
        calculator.inputNumber("20")
        calculator.inputOperation(CalculatorOperation.DIVIDE)
        calculator.inputNumber("4")
        val result = calculator.calculate()

        assertEquals("5", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test division by zero returns error`() {
        calculator.inputNumber("5")
        calculator.inputOperation(CalculatorOperation.DIVIDE)
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
        assertFalse(result.isError)
    }

    @Test
    fun `test multiple decimal points ignored`() {
        calculator.inputNumber("5")
        calculator.decimal()
        calculator.inputNumber("5")
        calculator.decimal()
        calculator.inputNumber("5")
        val result = calculator.getCurrentState()

        assertEquals("5.55", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test percentage calculation`() {
        calculator.inputNumber("50")
        val result = calculator.percent()

        assertEquals("0.5", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test percentage in operation`() {
        calculator.inputNumber("100")
        calculator.inputOperation(CalculatorOperation.MULTIPLY)
        calculator.inputNumber("50")
        calculator.percent()
        val result = calculator.calculate()

        assertEquals("50", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test clear function`() {
        calculator.inputNumber("123")
        calculator.inputOperation(CalculatorOperation.ADD)
        calculator.inputNumber("456")
        val result = calculator.clear()

        assertEquals("0", result.displayValue)
        assertEquals("", result.expression)
        assertFalse(result.isError)
    }

    @Test
    fun `test backspace on single digit`() {
        calculator.inputNumber("5")
        val result = calculator.backspace()

        assertEquals("0", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test backspace on multiple digits`() {
        calculator.inputNumber("123")
        calculator.backspace()
        val result = calculator.getCurrentState()

        assertEquals("12", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test toggle sign positive to negative`() {
        calculator.inputNumber("5")
        val result = calculator.toggleSign()

        assertEquals("-5", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test toggle sign negative to positive`() {
        calculator.inputNumber("5")
        calculator.toggleSign()
        val result = calculator.toggleSign()

        assertEquals("5", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test continuous operations`() {
        // 5 + 3 = 8, then + 2 = 10
        calculator.inputNumber("5")
        calculator.inputOperation(CalculatorOperation.ADD)
        calculator.inputNumber("3")
        calculator.calculate()

        calculator.inputOperation(CalculatorOperation.ADD)
        calculator.inputNumber("2")
        val result = calculator.calculate()

        assertEquals("10", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test expression building`() {
        calculator.inputNumber("25")
        calculator.inputOperation(CalculatorOperation.ADD)
        calculator.inputNumber("12")
        val state = calculator.getCurrentState()

        assertEquals("25 + 12", state.expression)
        assertEquals("12", state.displayValue)
        assertFalse(state.isError)
    }

    @Test
    fun `test operation change`() {
        calculator.inputNumber("10")
        calculator.inputOperation(CalculatorOperation.ADD)
        calculator.inputOperation(CalculatorOperation.SUBTRACT)
        val state = calculator.getCurrentState()

        assertEquals("10 -", state.expression)
        assertEquals("10", state.displayValue)
        assertFalse(state.isError)
    }

    @Test
    fun `test decimal with no leading digit`() {
        calculator.decimal()
        calculator.inputNumber("5")
        val result = calculator.getCurrentState()

        assertEquals("0.5", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test negative decimal number`() {
        calculator.inputNumber("3")
        calculator.decimal()
        calculator.inputNumber("14")
        calculator.toggleSign()
        val result = calculator.getCurrentState()

        assertEquals("-3.14", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test large number formatting`() {
        calculator.inputNumber("1000000")
        val result = calculator.getCurrentState()

        assertEquals("1,000,000", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test very small decimal`() {
        calculator.inputNumber("0")
        calculator.decimal()
        calculator.inputNumber("0001")
        val result = calculator.getCurrentState()

        assertEquals("0.0001", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test calculate without operation returns current state`() {
        calculator.inputNumber("42")
        val result = calculator.calculate()

        assertEquals("42", result.displayValue)
        assertFalse(result.isError)
    }

    @Test
    fun `test input operation without number`() {
        calculator.inputOperation(CalculatorOperation.ADD)
        val result = calculator.getCurrentState()

        assertEquals("0", result.displayValue)
        assertEquals("", result.expression)
        assertFalse(result.isError)
    }
}