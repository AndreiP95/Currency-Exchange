package com.example.currencyexchange.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyexchange.databinding.ActivityBaseBinding

open class BaseActivity : AppCompatActivity() {

    lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
