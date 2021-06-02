package com.example.smartmedicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.Auth0

class MainActivity : AppCompatActivity() {
    private lateinit var account: Auth0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}