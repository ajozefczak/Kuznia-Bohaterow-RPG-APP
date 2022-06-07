package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_instrukcja.*

class Instrukcja : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrukcja)

        InstrukcjaButtonRejestracja.setOnClickListener {
            InstrukcjaButtonRejestracja.isEnabled = false
            val rejestracjaIntent = Intent(this, InstrukcjaRejestracja::class.java)
            startActivity(rejestracjaIntent)
            InstrukcjaButtonRejestracja.isEnabled = true
        }

        InstrukcjaButtonLogowanie.setOnClickListener {
            InstrukcjaButtonLogowanie.isEnabled = false
            val InstrukcjaLogowanieIntent = Intent(this, InstrukcjaLogowanie::class.java)
            startActivity(InstrukcjaLogowanieIntent)
            InstrukcjaButtonLogowanie.isEnabled = true
        }

        InstrukcjaButtonWygląd.setOnClickListener {
            InstrukcjaButtonWygląd.isEnabled = false
            val InstrukcjaWylogujIntent = Intent(this, InstrukcjaWyloguj::class.java)
            startActivity(InstrukcjaWylogujIntent)
            InstrukcjaButtonWygląd.isEnabled = true
        }

        InstrukcjaButtonKalendarz.setOnClickListener {
            InstrukcjaButtonKalendarz.isEnabled = false
            val InstrukcjaKalendarzIntent = Intent(this, InstrukcjaKalendarz::class.java)
            startActivity(InstrukcjaKalendarzIntent)
            InstrukcjaButtonKalendarz.isEnabled = true
        }

        InstrukcjaButtonStworzPostac.setOnClickListener {
            InstrukcjaButtonStworzPostac.isEnabled = false
            val InstrukcjaStworzPostacIntent = Intent(this, InstrukcjaStworzPostac::class.java)
            startActivity(InstrukcjaStworzPostacIntent)
            InstrukcjaButtonStworzPostac.isEnabled = true
        }

        InstrukcjaButtonDolaczDoStolu.setOnClickListener {
            InstrukcjaButtonDolaczDoStolu.isEnabled = false
            val InstrukcjaDolaczDoStoluIntent = Intent(this, InstrukcjaDolaczDoStolu::class.java)
            startActivity(InstrukcjaDolaczDoStoluIntent)
            InstrukcjaButtonDolaczDoStolu.isEnabled = true
        }

        InstrukcjaButtonListaStolow.setOnClickListener {
            InstrukcjaButtonListaStolow.isEnabled = false
            val InstrukcjaListaStolowIntent = Intent(this, InstrukcjaListaStolow::class.java)
            startActivity(InstrukcjaListaStolowIntent)
            InstrukcjaButtonListaStolow.isEnabled = true
        }

        InstrukcjaButtonWyszukajPostac.setOnClickListener {
            InstrukcjaButtonWyszukajPostac.isEnabled = false
            val InstrukcjaWyszukajPostaciIntent = Intent(this, InstrukcjaWyszukajPostaci::class.java)
            startActivity(InstrukcjaWyszukajPostaciIntent)
            InstrukcjaButtonWyszukajPostac.isEnabled = true
        }

        InstrukcjaButtonTryby.setOnClickListener {
            InstrukcjaButtonTryby.isEnabled = false
            val InstrukcjaTrybGraczaMGIntent = Intent(this, InstrukcjaTrybGraczaMG::class.java)
            startActivity(InstrukcjaTrybGraczaMGIntent)
            InstrukcjaButtonTryby.isEnabled = true
        }

        InstrukcjaButtonStworzNowyStol.setOnClickListener {
            InstrukcjaButtonStworzNowyStol.isEnabled = false
            val InstrukcjaStworzNowyStolIntent = Intent(this, InstrukcjaStworzNowyStol::class.java)
            startActivity(InstrukcjaStworzNowyStolIntent)
            InstrukcjaButtonStworzNowyStol.isEnabled = true
        }

        button12.setOnClickListener {
            button12.isEnabled = false
            val InstrukcjaStolIntent = Intent(this, InstrukcjaStol::class.java)
            startActivity(InstrukcjaStolIntent)
            button12.isEnabled = true
        }

        InstrukcjaButtonCofnij.setOnClickListener {
            InstrukcjaButtonCofnij.isEnabled = false
            finish()
            InstrukcjaButtonCofnij.isEnabled = true
        }





    }
}