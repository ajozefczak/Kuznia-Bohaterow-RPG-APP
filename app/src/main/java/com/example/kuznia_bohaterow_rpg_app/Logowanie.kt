package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Logowanie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logowanie)
        initListeners()
    }


    private fun initListeners() {
        val LButtonRejestracja = findViewById<Button>(R.id.LButtonRejestracja)
        LButtonRejestracja.setOnClickListener(LButtonRejestracjaListener)
    }

    private val LButtonRejestracjaListener = View.OnClickListener { callRejestracjaActivity() }


    private fun callRejestracjaActivity() {
        val RejestracjaIntent = Intent(this, Rejestracja::class.java)
        startActivity(RejestracjaIntent)
    }
}