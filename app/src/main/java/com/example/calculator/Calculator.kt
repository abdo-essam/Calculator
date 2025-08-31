package com.example.calculator

import com.example.calculator.model.CalculatorOperation
import com.example.calculator.state.CalculatorState
import java.text.DecimalFormat

class Calculator {
    private var currentNumber = ""
    private var previousNumber = ""
    private var currentOperation: CalculatorOperation? = null
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

    fun inputOperation(operation: CalculatorOperation): CalculatorState {
        if (currentNumber.isNotEmpty()) {
            val existingOperation = currentOperation
            if (existingOperation != null && previousNumber.isNotEmpty()) {
                calculate()
            } else {
                previousNumber = currentNumber
            }

            currentOperation = operation
            currentNumber = ""
        } else if (previousNumber.isNotEmpty()) {
            currentOperation = operation
        }

        return getCurrentState()
    }

    fun calculate(): CalculatorState {
        val operation = currentOperation ?: return getCurrentState()

        if (currentNumber.isEmpty() || previousNumber.isEmpty()) {
            return getCurrentState()
        }

        val num1 = previousNumber.toDoubleOrNull() ?: 0.0
        val num2 = currentNumber.toDoubleOrNull() ?: 0.0

        val result = when (operation) {
            CalculatorOperation.ADD -> num1 + num2
            CalculatorOperation.SUBTRACT -> num1 - num2
            CalculatorOperation.MULTIPLY -> num1 * num2
            CalculatorOperation.DIVIDE -> {
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
            CalculatorOperation.PERCENT -> num1 * (num2 / 100)
            CalculatorOperation.EQUALS -> num1
        }

        previousNumber = result.toString()
        currentNumber = ""
        currentOperation = null
        isNewCalculation = true

        return getCurrentState()
    }

    fun clear(): CalculatorState {
        currentNumber = ""
        previousNumber = ""
        currentOperation = null
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
        val operation = currentOperation ?: return ""

        return when {
            previousNumber.isNotEmpty() && currentNumber.isNotEmpty() -> {
                "${formatNumber(previousNumber)} ${operation.symbol} ${formatNumber(currentNumber)}"
            }
            previousNumber.isNotEmpty() -> {
                "${formatNumber(previousNumber)} ${operation.symbol}"
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
}