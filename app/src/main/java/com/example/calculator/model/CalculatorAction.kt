package com.example.calculator.model

enum class CalculatorAction(val symbol: String) {
    CLEAR("AC"),
    BACKSPACE("←"),
    DECIMAL("."),
    TOGGLE_SIGN("±");

    companion object {
        fun fromSymbol(symbol: String): CalculatorAction? {
            return CalculatorAction.entries.find { it.symbol == symbol }
        }
    }
}