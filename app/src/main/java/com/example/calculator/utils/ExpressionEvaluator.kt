package com.example.calculator.utils

import java.util.*

object ExpressionEvaluator {

    fun evaluate(expression: String): Double {
        val cleanExpression = expression.replace("×", "*").replace("÷", "/")
        return evaluateExpression(cleanExpression)
    }

    private fun evaluateExpression(expression: String): Double {
        val tokens = tokenize(expression)
        val processedTokens = processPercentage(tokens)
        val postfix = infixToPostfix(processedTokens)
        return evaluatePostfix(postfix)
    }

    private fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        var currentNumber = ""

        for (char in expression) {
            when {
                char.isDigit() || char == '.' -> {
                    currentNumber += char
                }
                char in "+-*/" -> {
                    if (currentNumber.isNotEmpty()) {
                        tokens.add(currentNumber)
                        currentNumber = ""
                    }
                    tokens.add(char.toString())
                }
                char == '%' -> {
                    if (currentNumber.isNotEmpty()) {
                        tokens.add(currentNumber)
                        currentNumber = ""
                    }
                    tokens.add("%")
                }
                char == ' ' -> {
                    if (currentNumber.isNotEmpty()) {
                        tokens.add(currentNumber)
                        currentNumber = ""
                    }
                }
            }
        }

        if (currentNumber.isNotEmpty()) {
            tokens.add(currentNumber)
        }

        return tokens
    }

    private fun processPercentage(tokens: List<String>): List<String> {
        val processedTokens = mutableListOf<String>()
        var i = 0

        while (i < tokens.size) {
            when {
                // Handle pattern: number % number (e.g., "7 % 100" -> "7 * 100 / 100")
                i + 2 < tokens.size &&
                        tokens[i].toDoubleOrNull() != null &&
                        tokens[i + 1] == "%" &&
                        tokens[i + 2].toDoubleOrNull() != null -> {

                    val firstNumber = tokens[i]
                    val secondNumber = tokens[i + 2]

                    // Convert "7 % 100" to "7 * 100 / 100" which equals 7
                    processedTokens.add(firstNumber)
                    processedTokens.add("*")
                    processedTokens.add(secondNumber)
                    processedTokens.add("/")
                    processedTokens.add("100")

                    i += 3 // Skip the next two tokens as they're processed
                }
                // Handle standalone percentage: number % (convert to number/100)
                i + 1 < tokens.size &&
                        tokens[i].toDoubleOrNull() != null &&
                        tokens[i + 1] == "%" -> {

                    val number = tokens[i].toDouble()
                    val percentValue = (number / 100).toString()
                    processedTokens.add(percentValue)

                    i += 2 // Skip the % token
                }
                else -> {
                    processedTokens.add(tokens[i])
                    i++
                }
            }
        }

        return processedTokens
    }

    private fun infixToPostfix(tokens: List<String>): List<String> {
        val postfix = mutableListOf<String>()
        val operatorStack = Stack<String>()

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> postfix.add(token)
                token in "+-*/" -> {
                    while (operatorStack.isNotEmpty() &&
                        precedence(operatorStack.peek()) >= precedence(token)) {
                        postfix.add(operatorStack.pop())
                    }
                    operatorStack.push(token)
                }
            }
        }

        while (operatorStack.isNotEmpty()) {
            postfix.add(operatorStack.pop())
        }

        return postfix
    }

    private fun evaluatePostfix(postfix: List<String>): Double {
        val stack = Stack<Double>()

        for (token in postfix) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                token in "+-*/" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    val result = when (token) {
                        "+" -> a + b
                        "-" -> a - b
                        "*" -> a * b
                        "/" -> {
                            if (b == 0.0) throw ArithmeticException("Division by zero")
                            a / b
                        }
                        else -> throw IllegalArgumentException("Unknown operator: $token")
                    }
                    stack.push(result)
                }
            }
        }

        return stack.pop()
    }

    private fun precedence(operator: String): Int {
        return when (operator) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> 0
        }
    }
}