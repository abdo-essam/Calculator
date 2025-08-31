package com.example.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.Calculator
import com.example.calculator.model.CalculatorAction
import com.example.calculator.model.CalculatorNumber
import com.example.calculator.model.CalculatorOperation
import com.example.calculator.state.CalculatorState

class CalculatorViewModel : ViewModel() {

    private val calculator = Calculator()

    private val _calculatorState = MutableLiveData<CalculatorState>()
    val calculatorState: LiveData<CalculatorState> = _calculatorState

    init {
        _calculatorState.value = calculator.clear()
    }

    fun onNumberClick(number: CalculatorNumber) {
        _calculatorState.value = calculator.inputNumber(number.value)
    }

    fun onOperationClick(operation: CalculatorOperation) {
        when (operation) {
            CalculatorOperation.EQUALS -> _calculatorState.value = calculator.calculate()
            CalculatorOperation.PERCENT -> _calculatorState.value = calculator.percent()
            else -> _calculatorState.value = calculator.inputOperation(operation)
        }
    }

    fun onActionClick(action: CalculatorAction) {
        _calculatorState.value = when (action) {
            CalculatorAction.CLEAR -> calculator.clear()
            CalculatorAction.BACKSPACE -> calculator.backspace()
            CalculatorAction.DECIMAL -> calculator.decimal()
            CalculatorAction.TOGGLE_SIGN -> calculator.toggleSign()
        }
    }
}