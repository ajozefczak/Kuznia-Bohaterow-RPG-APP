package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Przedmioty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_przedmioty)
        initListeners()
    }
    private fun initListeners(){
        val ButtonDodajPrzedmiot = findViewById<Button>(R.id.PrzedmiotButtonDodajPrzedmiot)
        ButtonDodajPrzedmiot.setOnClickListener(ButtonDodajPrzedmiotListener)
    }
    private val ButtonDodajPrzedmiotListener = View.OnClickListener { callButtonDodajPrzedmiotListenerActivity() }

    private fun callButtonDodajPrzedmiotListenerActivity() {
        val DodajPrzedmiotIntent = Intent(this, DodajPrzedmiot::class.java)
        startActivity(DodajPrzedmiotIntent)
    }

}