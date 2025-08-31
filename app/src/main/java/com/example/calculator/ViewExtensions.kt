package com.example.calculator

import android.view.View

fun View.onClick(action: () -> Unit) {
    setOnClickListener { action() }
}