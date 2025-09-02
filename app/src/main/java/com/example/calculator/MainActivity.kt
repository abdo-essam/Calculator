package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.model.enums.CalculatorAction
import com.example.calculator.model.enums.CalculatorNumber
import com.example.calculator.model.enums.CalculatorOperation
import com.example.calculator.viewmodel.CalculatorViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
        observeViewModel()
    }

    private fun setupButtons() {
        with(binding) {
            // Number buttons
            btn0.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.ZERO) }
            btn1.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.ONE) }
            btn2.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.TWO) }
            btn3.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.THREE) }
            btn4.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.FOUR) }
            btn5.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.FIVE) }
            btn6.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.SIX) }
            btn7.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.SEVEN) }
            btn8.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.EIGHT) }
            btn9.setOnClickListener { viewModel.onNumberClick(CalculatorNumber.NINE) }

            // Operation buttons
            btnAdd.setOnClickListener { viewModel.onOperationClick(CalculatorOperation.ADD) }
            btnSubtract.setOnClickListener { viewModel.onOperationClick(CalculatorOperation.SUBTRACT) }
            btnMultiply.setOnClickListener { viewModel.onOperationClick(CalculatorOperation.MULTIPLY) }
            btnDivide.setOnClickListener { viewModel.onOperationClick(CalculatorOperation.DIVIDE) }
            btnPercent.setOnClickListener { viewModel.onOperationClick(CalculatorOperation.PERCENT) }
            btnEquals.setOnClickListener { viewModel.onOperationClick(CalculatorOperation.EQUALS) }

            // Action buttons
            btnAC.setOnClickListener { viewModel.onActionClick(CalculatorAction.CLEAR) }
            btnBackspace.setOnClickListener { viewModel.onActionClick(CalculatorAction.BACKSPACE) }
            btnDot.setOnClickListener { viewModel.onActionClick(CalculatorAction.DECIMAL) }
            btnPlusMinus.setOnClickListener { viewModel.onActionClick(CalculatorAction.TOGGLE_SIGN) }
        }
    }

    private fun observeViewModel() {
        viewModel.calculatorState.observe(this) { state ->
            with(binding) {
                tvResult.text = state.displayValue

                // Show current expression or previous completed expression
                tvExpression.text = when {
                    state.isCalculated && state.previousExpression.isNotEmpty() ->
                        state.previousExpression
                    state.expression.isNotEmpty() -> state.expression
                    else -> ""
                }
            }
        }
    }
}