package com.example.calculator.extensions

import android.view.View

fun View.onClick(action: () -> Unit) {
    setOnClickListener { action() }
}