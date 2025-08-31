package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCalculator()
        updateDisplay(calculator.clear())
    }

    private fun setupCalculator() {
        with(binding) {
            // Number buttons
            listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
                .forEachIndexed { index, button ->
                    button.onClick { handleNumberInput(index.toString()) }
                }

            // Operators
            btnAdd.onClick { handleOperatorInput("+") }
            btnSubtract.onClick { handleOperatorInput("-") }
            btnMultiply.onClick { handleOperatorInput("×") }
            btnDivide.onClick { handleOperatorInput("/") }

            // Functions
            btnAC.onClick { updateDisplay(calculator.clear()) }
            btnBackspace.onClick { updateDisplay(calculator.backspace()) }
            btnDot.onClick { updateDisplay(calculator.decimal()) }
            btnPlusMinus.onClick { updateDisplay(calculator.toggleSign()) }
            btnPercent.onClick { updateDisplay(calculator.percent()) }
            btnEquals.onClick { updateDisplay(calculator.calculate()) }
        }
    }

    private fun handleNumberInput(number: String) {
        updateDisplay(calculator.inputNumber(number))
    }

    private fun handleOperatorInput(operator: String) {
        updateDisplay(calculator.inputOperator(operator))
    }

    private fun updateDisplay(state: Calculator.CalculatorState) {
        binding.tvResult.text = state.displayValue
        binding.tvExpression.text = state.expression
    }
}