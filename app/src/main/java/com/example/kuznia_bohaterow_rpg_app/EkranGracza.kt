package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_ekran_gracza.*

class EkranGracza : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ekran_gracza)
        initListeners()

        GButtonWyloguj.setOnClickListener {
            GButtonWyloguj.isEnabled = false
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Poprawnie wylogowano sie z systemu", Toast.LENGTH_SHORT).show()
            GButtonWyloguj.isEnabled = true
            finish()
        }

        GButtonPostac.setOnClickListener {
            GButtonPostac.isEnabled = false
            val tworzeniePostaciIntent = Intent(this, TworzeniePostaci::class.java)
            startActivity(tworzeniePostaciIntent)
            GButtonPostac.isEnabled = true
        }

        GButtonWyszukajPostac.setOnClickListener {
            GButtonWyszukajPostac.isEnabled = false
            val listaPostaciIntent = Intent(this, ListaPostaci::class.java)
            startActivity(listaPostaciIntent)
            GButtonWyszukajPostac.isEnabled = true
        }
    }

    private fun initListeners() {
        val GButtonKalendarz = findViewById<Button>(R.id.GButtonKalendarz)
        GButtonKalendarz.setOnClickListener(GButtonKalendarzListener)
    }

    private val GButtonKalendarzListener = View.OnClickListener { callKalendarzActivity() }

    private fun callKalendarzActivity() {
        GButtonKalendarz.isEnabled = false
        val KalendarzIntent = Intent(this, Kalendarz::class.java)
        startActivity(KalendarzIntent)
        GButtonKalendarz.isEnabled = true
    }

}