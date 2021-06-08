package com.example.smartmedicapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartmedicapp.CredentialsManager.CredentialsManager

/**
 * Activity that handles user interactions if user is not logged in
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (CredentialsManager.IsLoggedIn) {
            val intent = Intent (this, LoggedActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
        }


    }

    override fun onRestart() {
        super.onRestart()
        // if user is logged in then skip login screen

        if (CredentialsManager.IsLoggedIn) {
            val intent = Intent (this, LoggedActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
        }
    }
}