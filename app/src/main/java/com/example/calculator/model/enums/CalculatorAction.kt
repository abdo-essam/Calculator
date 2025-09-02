package com.example.calculator.model.enums

enum class CalculatorAction(val symbol: String) {
    CLEAR("AC"),
    BACKSPACE("←"),
    DECIMAL("."),
    TOGGLE_SIGN("±");
}