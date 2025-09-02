package com.example.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.model.calculator.Calculator
import com.example.calculator.model.calculator.CalculatorEngine
import com.example.calculator.model.enums.CalculatorAction
import com.example.calculator.model.enums.CalculatorNumber
import com.example.calculator.model.enums.CalculatorOperation
import com.example.calculator.state.CalculatorState

class CalculatorViewModel : ViewModel() {

    private val calculatorEngine: CalculatorEngine = Calculator()

    private val _calculatorState = MutableLiveData<CalculatorState>()
    val calculatorState: LiveData<CalculatorState> = _calculatorState

    init {
        _calculatorState.value = calculatorEngine.clear()
    }

    fun onNumberClick(number: CalculatorNumber) {
        _calculatorState.value = calculatorEngine.inputNumber(number.value)
    }

    fun onOperationClick(operation: CalculatorOperation) {
        _calculatorState.value = calculatorEngine.inputOperation(operation)
    }

    fun onActionClick(action: CalculatorAction) {
        _calculatorState.value = calculatorEngine.inputAction(action)
    }
}