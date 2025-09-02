package com.example.calculator.state

data class CalculatorState(
    val displayValue: String = "0",
    val expression: String = "",
    val previousExpression: String = "",
    val isError: Boolean = false,
    val isCalculated: Boolean = false
)