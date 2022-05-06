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

        GButtonListaStoly.setOnClickListener {
            GButtonListaStoly.isEnabled = false
            val listStolowIntent = Intent(this, ListaStoly::class.java)
            startActivity(listStolowIntent)
            GButtonListaStoly.isEnabled = true
        }
    }

    private fun initListeners() {
        val GButtonKalendarz = findViewById<Button>(R.id.GButtonKalendarz)
        GButtonKalendarz.setOnClickListener(GButtonKalendarzListener)

        val GButtonDodajSt = findViewById<Button>(R.id.GButtonTworzenieStołu)
        GButtonDodajSt.setOnClickListener(GButtonDodajStolListener)

        GButtonSwitchPlayer.setOnClickListener {
            if (GButtonSwitchPlayer.isChecked) {
                GButtonTworzenieStołu.visibility = View.VISIBLE;
                GButtonWyszukajPostac.visibility = View.INVISIBLE;
                idLayoutDolacz.visibility = View.INVISIBLE;
                GButtonPostac.visibility = View.INVISIBLE;
                GButtonSwitchPlayer.text = "Tryb MG"
            } else {
                GButtonTworzenieStołu.visibility = View.INVISIBLE;
                GButtonWyszukajPostac.visibility = View.VISIBLE;
                idLayoutDolacz.visibility = View.VISIBLE;
                GButtonPostac.visibility = View.VISIBLE;
                GButtonSwitchPlayer.text = "Tryb Gracza"
            }
        }
    }

    private val GButtonKalendarzListener = View.OnClickListener { callKalendarzActivity() }
    private val GButtonDodajStolListener = View.OnClickListener { callDodajStolActivity() }

    private fun callKalendarzActivity() {
        GButtonKalendarz.isEnabled = false
        val KalendarzIntent = Intent(this, Kalendarz::class.java)
        startActivity(KalendarzIntent)
        GButtonKalendarz.isEnabled = true
    }

    private fun callDodajStolActivity() {
        val DodajStolIntent = Intent(this, DodajStol::class.java)
        val idCharacter = intent.getStringExtra("id").toString()
        DodajStolIntent.putExtra("id",idCharacter)
        startActivity(DodajStolIntent)
    }
}