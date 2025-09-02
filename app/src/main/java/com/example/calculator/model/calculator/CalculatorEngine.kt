package com.example.calculator.model.calculator


import com.example.calculator.model.enums.CalculatorAction
import com.example.calculator.model.enums.CalculatorOperation
import com.example.calculator.state.CalculatorState

interface CalculatorEngine {
    fun inputNumber(number: String): CalculatorState
    fun inputOperation(operation: CalculatorOperation): CalculatorState
    fun inputAction(action: CalculatorAction): CalculatorState
    fun calculate(): CalculatorState
    fun clear(): CalculatorState
    fun getCurrentState(): CalculatorState
}