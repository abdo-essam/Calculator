package com.example.calculator.model

enum class CalculatorNumber(val value: String) {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");

    companion object {
        fun fromValue(value: String): CalculatorNumber? {
            return CalculatorNumber.entries.find { it.value == value }
        }
    }
}