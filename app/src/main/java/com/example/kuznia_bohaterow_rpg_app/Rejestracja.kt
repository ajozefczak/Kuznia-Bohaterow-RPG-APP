package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class Rejestracja : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejestracja)
        initListeners()

        setupHyperlink()

    }

    fun setupHyperlink() {
        val linkTextView = findViewById<TextView>(R.id.regulamin)
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextView.setLinkTextColor(Color.BLUE)
    }


    private fun initListeners() {
        val RButtonLogowanie = findViewById<Button>(R.id.RButtonLogowanie)
        RButtonLogowanie.setOnClickListener(RButtonLogowanieListener)
    }

    private val RButtonLogowanieListener = View.OnClickListener { callLogowanieActivity() }


    private fun callLogowanieActivity() {
        val LogowanieIntent = Intent(this, Logowanie::class.java)
        startActivity(LogowanieIntent)
    }

}