package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_logowanie.*

class Logowanie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logowanie)
        initListeners()


        LButtonPrzzywruc.setOnClickListener {
            LButtonPrzzywruc.isEnabled = false
            val ResetHaslaIntent = Intent(this, ResetHasla::class.java)
            startActivity(ResetHaslaIntent)
            LButtonPrzzywruc.isEnabled = true
            finish()
        }

        LButtonZaloguj.setOnClickListener{
            LButtonZaloguj.isEnabled = false
            when{
                TextUtils.isEmpty(editTextTextEmailAddress.text.toString()) -> {
                    Toast.makeText(this,getString(R.string.WprowadzEmail),Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(editTextTextPassword.text.toString()) -> {
                    Toast.makeText(this,R.string.WprowadzHaslo,Toast.LENGTH_SHORT).show()
                }

                else -> {

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextTextEmailAddress.text.toString(),editTextTextPassword.text.toString()).addOnCompleteListener{
                        task ->
                        if(task.isSuccessful){
                            Toast.makeText(this,R.string.PoprawnieZalogowano,Toast.LENGTH_SHORT).show()
                            val EkranGraczaIntent = Intent(this, EkranGracza::class.java)
                            startActivity(EkranGraczaIntent)
                            LButtonZaloguj.isEnabled = true
                            finish()
                        }else{
                            LButtonZaloguj.isEnabled = true
                            Toast.makeText(this,R.string.WystapilBladPodczasLogowania,Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
        }

    }


    private fun initListeners() {
        val LButtonRejestracja = findViewById<Button>(R.id.LButtonRejestracja)
        LButtonRejestracja.setOnClickListener(LButtonRejestracjaListener)
    }

    private val LButtonRejestracjaListener = View.OnClickListener { callRejestracjaActivity() }


    private fun callRejestracjaActivity() {
        LButtonRejestracja.isEnabled = false
        val RejestracjaIntent = Intent(this, Rejestracja::class.java)
        startActivity(RejestracjaIntent)
        LButtonRejestracja.isEnabled = true
        finish()
    }
}