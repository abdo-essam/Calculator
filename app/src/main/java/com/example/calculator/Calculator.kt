package com.example.calculator

import java.text.DecimalFormat

class Calculator {
    private var currentNumber = ""
    private var previousNumber = ""
    private var currentOperator = ""
    private var isNewCalculation = true

    private val decimalFormat = DecimalFormat("#,###.########")

    fun inputNumber(number: String): CalculatorState {
        if (isNewCalculation) {
            currentNumber = ""
            isNewCalculation = false
        }

        currentNumber += number
        return getCurrentState()
    }

    fun inputOperator(operator: String): CalculatorState {
        if (currentNumber.isNotEmpty()) {
            if (currentOperator.isNotEmpty() && previousNumber.isNotEmpty()) {
                calculate()
            } else {
                previousNumber = currentNumber
            }

            currentOperator = operator
            currentNumber = ""
        } else if (previousNumber.isNotEmpty()) {
            currentOperator = operator
        }

        return getCurrentState()
    }

    fun calculate(): CalculatorState {
        if (currentNumber.isEmpty() || previousNumber.isEmpty() || currentOperator.isEmpty()) {
            return getCurrentState()
        }

        val num1 = previousNumber.toDoubleOrNull() ?: 0.0
        val num2 = currentNumber.toDoubleOrNull() ?: 0.0

        val result = when (currentOperator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "×" -> num1 * num2
            "/" -> {
                if (num2 != 0.0) {
                    num1 / num2
                } else {
                    return CalculatorState(
                        displayValue = "Error",
                        expression = "",
                        isError = true
                    )
                }
            }
            else -> 0.0
        }

        previousNumber = result.toString()
        currentNumber = ""
        currentOperator = ""
        isNewCalculation = true

        return getCurrentState()
    }

    fun clear(): CalculatorState {
        currentNumber = ""
        previousNumber = ""
        currentOperator = ""
        isNewCalculation = true
        return CalculatorState("0", "")
    }

    fun backspace(): CalculatorState {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
        }
        return getCurrentState()
    }

    fun decimal(): CalculatorState {
        if (isNewCalculation) {
            currentNumber = "0"
            isNewCalculation = false
        }

        if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0"
            }
            currentNumber += "."
        }
        return getCurrentState()
    }

    fun toggleSign(): CalculatorState {
        if (currentNumber.isNotEmpty() && currentNumber != "0") {
            currentNumber = if (currentNumber.startsWith("-")) {
                currentNumber.substring(1)
            } else {
                "-$currentNumber"
            }
        }
        return getCurrentState()
    }

    fun percent(): CalculatorState {
        if (currentNumber.isNotEmpty()) {
            val value = currentNumber.toDoubleOrNull() ?: 0.0
            currentNumber = (value / 100).toString()
        }
        return getCurrentState()
    }

    fun getCurrentState(): CalculatorState {
        val displayValue = when {
            currentNumber.isNotEmpty() -> formatNumber(currentNumber)
            previousNumber.isNotEmpty() -> formatNumber(previousNumber)
            else -> "0"
        }

        val expression = buildExpression()

        return CalculatorState(displayValue, expression)
    }

    private fun buildExpression(): String {
        return when {
            previousNumber.isNotEmpty() && currentOperator.isNotEmpty() && currentNumber.isNotEmpty() -> {
                "${formatNumber(previousNumber)} $currentOperator ${formatNumber(currentNumber)}"
            }
            previousNumber.isNotEmpty() && currentOperator.isNotEmpty() -> {
                "${formatNumber(previousNumber)} $currentOperator"
            }
            else -> ""
        }
    }

    private fun formatNumber(number: String): String {
        if (number.isEmpty()) return "0"

        return try {
            val value = number.toDouble()
            decimalFormat.format(value)
        } catch (e: NumberFormatException) {
            number
        }
    }

    data class CalculatorState(
        val displayValue: String,
        val expression: String,
        val isError: Boolean = false
    )
}