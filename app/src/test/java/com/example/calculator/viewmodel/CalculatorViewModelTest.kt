package com.example.calculator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.calculator.model.enums.CalculatorAction
import com.example.calculator.model.enums.CalculatorNumber
import com.example.calculator.model.enums.CalculatorOperation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalculatorViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setup() {
        viewModel = CalculatorViewModel()
    }

    @Test
    fun `test initial state`() {
        val initialState = viewModel.calculatorState.value

        assertNotNull(initialState)
        assertEquals("0", initialState?.displayValue)
        assertEquals("", initialState?.expression)
        assertFalse(initialState?.isError ?: true)
    }

    @Test
    fun `test number input updates state`() {
        viewModel.onNumberClick(CalculatorNumber.FIVE)

        val state = viewModel.calculatorState.value
        assertEquals("5", state?.displayValue)
        assertFalse(state?.isError ?: true)
    }

    @Test
    fun `test multiple number inputs`() {
        viewModel.onNumberClick(CalculatorNumber.ONE)
        viewModel.onNumberClick(CalculatorNumber.TWO)
        viewModel.onNumberClick(CalculatorNumber.THREE)

        val state = viewModel.calculatorState.value
        assertEquals("123", state?.displayValue)
    }

    @Test
    fun `test operation input`() {
        viewModel.onNumberClick(CalculatorNumber.TWO)
        viewModel.onOperationClick(CalculatorOperation.ADD)

        val state = viewModel.calculatorState.value
        assertEquals("2 +", state?.expression)
        assertEquals("2", state?.displayValue)
    }

    @Test
    fun `test clear action`() {
        viewModel.onNumberClick(CalculatorNumber.NINE)
        viewModel.onActionClick(CalculatorAction.CLEAR)

        val state = viewModel.calculatorState.value
        assertEquals("0", state?.displayValue)
        assertEquals("", state?.expression)
    }

    @Test
    fun `test decimal action`() {
        viewModel.onNumberClick(CalculatorNumber.THREE)
        viewModel.onActionClick(CalculatorAction.DECIMAL)
        viewModel.onNumberClick(CalculatorNumber.ONE)
        viewModel.onNumberClick(CalculatorNumber.FOUR)

        val state = viewModel.calculatorState.value
        assertEquals("3.14", state?.displayValue)
    }

    @Test
    fun `test toggle sign action`() {
        viewModel.onNumberClick(CalculatorNumber.FIVE)
        viewModel.onActionClick(CalculatorAction.TOGGLE_SIGN)

        val state = viewModel.calculatorState.value
        assertEquals("-5", state?.displayValue)
    }

    @Test
    fun `test backspace action`() {
        viewModel.onNumberClick(CalculatorNumber.ONE)
        viewModel.onNumberClick(CalculatorNumber.TWO)
        viewModel.onNumberClick(CalculatorNumber.THREE)
        viewModel.onActionClick(CalculatorAction.BACKSPACE)

        val state = viewModel.calculatorState.value
        assertEquals("12", state?.displayValue)
    }

    @Test
    fun `test percent operation`() {
        viewModel.onNumberClick(CalculatorNumber.FIVE)
        viewModel.onNumberClick(CalculatorNumber.ZERO)
        viewModel.onOperationClick(CalculatorOperation.PERCENT)

        val state = viewModel.calculatorState.value
        assertEquals("0.5", state?.displayValue)
    }

    @Test
    fun `test division by zero`() {
        viewModel.onNumberClick(CalculatorNumber.FIVE)
        viewModel.onOperationClick(CalculatorOperation.DIVIDE)
        viewModel.onNumberClick(CalculatorNumber.ZERO)
        viewModel.onOperationClick(CalculatorOperation.EQUALS)

        val state = viewModel.calculatorState.value
        assertEquals("Error", state?.displayValue)
        assertTrue(state?.isError ?: false)
    }

    @Test
    fun `test chained operations`() {
        // 5 + 3 = 8, then - 2 = 6
        viewModel.onNumberClick(CalculatorNumber.FIVE)
        viewModel.onOperationClick(CalculatorOperation.ADD)
        viewModel.onNumberClick(CalculatorNumber.THREE)
        viewModel.onOperationClick(CalculatorOperation.SUBTRACT)

        var state = viewModel.calculatorState.value
        assertEquals("8", state?.displayValue)
        assertEquals("8 -", state?.expression)

        viewModel.onNumberClick(CalculatorNumber.TWO)
        viewModel.onOperationClick(CalculatorOperation.EQUALS)

        state = viewModel.calculatorState.value
        assertEquals("6", state?.displayValue)
    }
}