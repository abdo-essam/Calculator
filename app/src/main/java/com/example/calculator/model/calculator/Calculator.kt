package com.example.calculator.model.calculator

import com.example.calculator.model.enums.CalculatorAction
import com.example.calculator.model.enums.CalculatorOperation
import com.example.calculator.state.CalculatorState
import com.example.calculator.utils.NumberFormatter
import com.example.calculator.utils.ExpressionEvaluator

class Calculator : CalculatorEngine {
    private var currentExpression = ""
    private var displayNumber = "0"
    private var previousExpression = ""
    private var isNewCalculation = true
    private var lastResult = ""
    private var isCalculated = false

    override fun inputNumber(number: String): CalculatorState {
        if (isNewCalculation || isCalculated) {
            currentExpression = ""
            displayNumber = ""
            previousExpression = ""
            isNewCalculation = false
            isCalculated = false
        }

        displayNumber += number
        return getCurrentState()
    }

    override fun inputOperation(operation: CalculatorOperation): CalculatorState {
        return when (operation) {
            CalculatorOperation.EQUALS -> calculate()
            CalculatorOperation.PERCENT -> handlePercentAsOperation()
            else -> handleOperation(operation)
        }
    }

    override fun inputAction(action: CalculatorAction): CalculatorState {
        return when (action) {
            CalculatorAction.CLEAR -> clear()
            CalculatorAction.BACKSPACE -> backspace()
            CalculatorAction.DECIMAL -> decimal()
            CalculatorAction.TOGGLE_SIGN -> toggleSign()
        }
    }

    private fun handleOperation(operation: CalculatorOperation): CalculatorState {
        if (isCalculated) {
            // Start new expression with the last result
            currentExpression = lastResult
            displayNumber = ""
            isCalculated = false
            previousExpression = ""
        }

        if (displayNumber.isNotEmpty()) {
            currentExpression += displayNumber
            displayNumber = ""
        }

        // Replace the last operation if we're adding consecutive operations
        if (currentExpression.isNotEmpty() && isLastCharOperation()) {
            currentExpression = currentExpression.dropLast(3) // Remove " op "
        }

        currentExpression += " ${operation.symbol} "
        return getCurrentState()
    }

    private fun handlePercentAsOperation(): CalculatorState {
        if (isCalculated) {
            // Start new expression with the last result
            currentExpression = lastResult
            displayNumber = ""
            isCalculated = false
            previousExpression = ""
        }

        if (displayNumber.isNotEmpty()) {
            currentExpression += displayNumber
            displayNumber = ""
        }

        // Replace the last operation if we're adding consecutive operations
        if (currentExpression.isNotEmpty() && isLastCharOperation()) {
            currentExpression = currentExpression.dropLast(3) // Remove " op "
        }

        currentExpression += " % "
        return getCurrentState()
    }

    override fun calculate(): CalculatorState {
        if (displayNumber.isNotEmpty()) {
            currentExpression += displayNumber
        }

        if (currentExpression.isEmpty()) {
            return getCurrentState()
        }

        return try {
            val result = ExpressionEvaluator.evaluate(currentExpression)

            previousExpression = "$currentExpression ="
            lastResult = result.toString()
            displayNumber = NumberFormatter.format(result.toString())
            currentExpression = ""
            isCalculated = true

            CalculatorState(
                displayValue = displayNumber,
                expression = "",
                previousExpression = previousExpression,
                isCalculated = true
            )
        } catch (e: Exception) {
            CalculatorState(
                displayValue = "Error",
                expression = "",
                previousExpression = "",
                isError = true
            )
        }
    }

    override fun clear(): CalculatorState {
        currentExpression = ""
        displayNumber = "0"
        previousExpression = ""
        lastResult = ""
        isNewCalculation = true
        isCalculated = false
        return CalculatorState("0", "")
    }

    private fun backspace(): CalculatorState {
        if (isCalculated) {
            return clear()
        }

        if (displayNumber.isNotEmpty()) {
            displayNumber = displayNumber.dropLast(1)
            if (displayNumber.isEmpty()) {
                displayNumber = "0"
            }
        }
        return getCurrentState()
    }

    private fun decimal(): CalculatorState {
        if (isCalculated) {
            currentExpression = ""
            displayNumber = "0"
            previousExpression = ""
            isCalculated = false
        }

        if (displayNumber.isEmpty() || displayNumber == "0") {
            displayNumber = "0"
        }

        if (!displayNumber.contains(".")) {
            displayNumber += "."
        }

        return getCurrentState()
    }

    private fun toggleSign(): CalculatorState {
        if (isCalculated) return getCurrentState()

        if (displayNumber.isNotEmpty() && displayNumber != "0") {
            displayNumber = if (displayNumber.startsWith("-")) {
                displayNumber.substring(1)
            } else {
                "-$displayNumber"
            }
        }
        return getCurrentState()
    }

    private fun isLastCharOperation(): Boolean {
        val trimmed = currentExpression.trim()
        return trimmed.isNotEmpty() && (
                trimmed.endsWith(" +") ||
                        trimmed.endsWith(" -") ||
                        trimmed.endsWith(" ×") ||
                        trimmed.endsWith(" /") ||
                        trimmed.endsWith(" %")
                )
    }

    override fun getCurrentState(): CalculatorState {
        val currentDisplayExpression = if (isCalculated) {
            ""
        } else {
            buildCurrentExpression()
        }

        return CalculatorState(
            displayValue = if (displayNumber.isEmpty()) "0" else NumberFormatter.format(displayNumber),
            expression = currentDisplayExpression,
            previousExpression = previousExpression,
            isCalculated = isCalculated
        )
    }

    private fun buildCurrentExpression(): String {
        return when {
            currentExpression.isEmpty() && displayNumber.isNotEmpty() -> ""
            displayNumber.isNotEmpty() -> currentExpression + displayNumber
            else -> currentExpression.trimEnd()
        }
    }
}