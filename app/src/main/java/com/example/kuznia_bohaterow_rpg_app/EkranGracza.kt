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
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Poprawnie wylogowano sie z systemu", Toast.LENGTH_SHORT).show()
            finish()
        }

        GButtonPostac.setOnClickListener {
            val tworzeniePostaciIntent = Intent(this, TworzeniePostaci::class.java)
            startActivity(tworzeniePostaciIntent)
            finish()
        }

        GButtonWyszukajPostac.setOnClickListener {
            val listaPostaciIntent = Intent(this, ListaPostaci::class.java)
            startActivity(listaPostaciIntent)
            finish()
        }
    }

    private fun initListeners() {
        val GButtonKalendarz = findViewById<Button>(R.id.GButtonKalendarz)
        GButtonKalendarz.setOnClickListener(GButtonKalendarzListener)
    }

    private val GButtonKalendarzListener = View.OnClickListener { callKalendarzActivity() }

    private fun callKalendarzActivity() {
        val KalendarzIntent = Intent(this, Kalendarz::class.java)
        startActivity(KalendarzIntent)
        finish()
    }

}