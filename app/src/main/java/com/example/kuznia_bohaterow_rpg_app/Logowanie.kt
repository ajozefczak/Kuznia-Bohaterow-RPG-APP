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
            val ResetHaslaIntent = Intent(this, ResetHasla::class.java)
            startActivity(ResetHaslaIntent)
            finish()
        }

        LButtonZaloguj.setOnClickListener{
            when{
                TextUtils.isEmpty(editTextTextEmailAddress.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź adres email",Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(editTextTextPassword.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź hasło",Toast.LENGTH_SHORT).show()
                }

                else -> {

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextTextEmailAddress.text.toString(),editTextTextPassword.text.toString()).addOnCompleteListener{
                        task ->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Poprawnie zalogowano sie do systemu",Toast.LENGTH_SHORT).show()

                            val EkranGraczaIntent = Intent(this, EkranGracza::class.java)
                            startActivity(EkranGraczaIntent)
                            finish()
                        }else{
                            Toast.makeText(this,"Wystąpił błąd podczas logowania. Użytkownik nie istnieje lub podane dane są błędne",Toast.LENGTH_LONG).show()
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
        val RejestracjaIntent = Intent(this, Rejestracja::class.java)
        startActivity(RejestracjaIntent)
        finish()
    }
}