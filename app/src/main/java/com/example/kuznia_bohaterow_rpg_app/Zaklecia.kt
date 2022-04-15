package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Zaklecia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zaklecia)
        initListeners()
    }
    private fun initListeners(){
        val ButtonDodajZaklecie = findViewById<Button>(R.id.ZakleciaButtonDodajZaklecie)
        ButtonDodajZaklecie.setOnClickListener(ButtonDodajZaklecieListener)
    }
    private val ButtonDodajZaklecieListener = View.OnClickListener { callButtonDodajZaklecieListenerActivity() }

    private fun callButtonDodajZaklecieListenerActivity() {
        val DodajZaklecieIntent = Intent(this, DodajZaklecie::class.java)
        startActivity(DodajZaklecieIntent)
    }
}