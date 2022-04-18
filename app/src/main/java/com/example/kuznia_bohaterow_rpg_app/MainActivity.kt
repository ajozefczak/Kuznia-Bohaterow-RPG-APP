package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
    }

    private fun initListeners(){
        val ButtonLogowanie = findViewById<Button>(R.id.ButtonLogowanie)
        ButtonLogowanie.setOnClickListener(ButtonLogowanieListener)

        val ButtonRejestracja = findViewById<Button>(R.id.ButtonRejestracja)
        ButtonRejestracja.setOnClickListener(ButtonRejestracjaListener)

    }

    private val ButtonLogowanieListener = View.OnClickListener { callLogowanieActivity() }
    private val ButtonRejestracjaListener = View.OnClickListener { callRejestracjaActivity() }

    private fun callLogowanieActivity() {
        ButtonLogowanie.isEnabled = false
        val LogowanieIntent = Intent(this, Logowanie::class.java)
        startActivity(LogowanieIntent)
        ButtonLogowanie.isEnabled = true
    }

    private fun callRejestracjaActivity() {
        ButtonRejestracja.isEnabled = false
        val RejestracjaIntent = Intent(this, Rejestracja::class.java)
        startActivity(RejestracjaIntent)
        ButtonRejestracja.isEnabled = true
    }

}