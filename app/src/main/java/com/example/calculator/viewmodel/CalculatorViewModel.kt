package com.example.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.Calculator

class CalculatorViewModel : ViewModel() {

    private val calculator = Calculator()

    private val _calculatorState = MutableLiveData<Calculator.CalculatorState>()
    val calculatorState: LiveData<Calculator.CalculatorState> = _calculatorState

    init {
        _calculatorState.value = calculator.clear()
    }

    fun onNumberClick(number: String) {
        _calculatorState.value = calculator.inputNumber(number)
    }

    fun onOperatorClick(operator: String) {
        _calculatorState.value = calculator.inputOperator(operator)
    }

    fun onEqualsClick() {
        _calculatorState.value = calculator.calculate()
    }

    fun onClearClick() {
        _calculatorState.value = calculator.clear()
    }

    fun onBackspaceClick() {
        _calculatorState.value = calculator.backspace()
    }

    fun onDecimalClick() {
        _calculatorState.value = calculator.decimal()
    }

    fun onToggleSignClick() {
        _calculatorState.value = calculator.toggleSign()
    }

    fun onPercentClick() {
        _calculatorState.value = calculator.percent()
    }
}