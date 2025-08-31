package com.example.calculator.model

enum class CalculatorOperation(val symbol: String) {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("×"),
    DIVIDE("/"),
    PERCENT("%"),
    EQUALS("=");

    companion object {
        fun fromSymbol(symbol: String): CalculatorOperation? {
            return CalculatorOperation.entries.find { it.symbol == symbol }
        }
    }
}