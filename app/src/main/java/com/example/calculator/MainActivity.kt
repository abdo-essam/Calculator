package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
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
            btn0.setOnClickListener { viewModel.onNumberClick("0") }
            btn1.setOnClickListener { viewModel.onNumberClick("1") }
            btn2.setOnClickListener { viewModel.onNumberClick("2") }
            btn3.setOnClickListener { viewModel.onNumberClick("3") }
            btn4.setOnClickListener { viewModel.onNumberClick("4") }
            btn5.setOnClickListener { viewModel.onNumberClick("5") }
            btn6.setOnClickListener { viewModel.onNumberClick("6") }
            btn7.setOnClickListener { viewModel.onNumberClick("7") }
            btn8.setOnClickListener { viewModel.onNumberClick("8") }
            btn9.setOnClickListener { viewModel.onNumberClick("9") }

            // Operator buttons
            btnAdd.setOnClickListener { viewModel.onOperatorClick("+") }
            btnSubtract.setOnClickListener { viewModel.onOperatorClick("-") }
            btnMultiply.setOnClickListener { viewModel.onOperatorClick("×") }
            btnDivide.setOnClickListener { viewModel.onOperatorClick("/") }

            // Function buttons
            btnAC.setOnClickListener { viewModel.onClearClick() }
            btnBackspace.setOnClickListener { viewModel.onBackspaceClick() }
            btnDot.setOnClickListener { viewModel.onDecimalClick() }
            btnPlusMinus.setOnClickListener { viewModel.onToggleSignClick() }
            btnPercent.setOnClickListener { viewModel.onPercentClick() }
            btnEquals.setOnClickListener { viewModel.onEqualsClick() }
        }
    }

    private fun observeViewModel() {
        viewModel.calculatorState.observe(this) { state ->
            binding.tvResult.text = state.displayValue
            binding.tvExpression.text = state.expression
        }
    }
}