package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_rejestracja.*
import java.lang.Exception


class Rejestracja : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejestracja)

        initListeners()
        setupHyperlink()

        // Code to register pepega acc

        RButtonRejestracja.setOnClickListener{
            when{
                TextUtils.isEmpty(editTextTextEmailAddress2.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź adres email",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(editTextTextPassword2.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź hasło",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(editTextTextPassword3.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź ponownie haslo",Toast.LENGTH_SHORT).show()
                }


                /*if(editTextTextPassword2.text.toString() != editTextTextPassword3.text.toString()) -> {
                    Toast.makeText(this,"Podane hasła różnią sie",Toast.LENGTH_SHORT).show()
                }*/


                else -> {
                    val email: String = editTextTextEmailAddress2.text.toString()


                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,editTextTextPassword2.text.toString()).addOnCompleteListener {
                            task -> if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            Toast.makeText(this,"Chyba działa",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }

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

    private fun registerAcc(){

    }

}