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
import com.google.firebase.firestore.FirebaseFirestore
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
                TextUtils.isEmpty(editTextTextPersonName.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź nick",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(editTextTextEmailAddress2.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź adres email",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(editTextTextPassword2.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź hasło",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(editTextTextPassword3.text.toString()) -> {
                    Toast.makeText(this,"Wprowadź ponownie haslo",Toast.LENGTH_SHORT).show()
                }
                !editTextTextPassword2.text.toString().equals(editTextTextPassword3.text.toString()) -> {
                    Toast.makeText(this,"Hasła nie są takie same",Toast.LENGTH_SHORT).show()
                }
                !checkBox.isChecked -> {
                    Toast.makeText(this,"W celu utworzenia konta wymagana jest akceptacja regulaminu",Toast.LENGTH_SHORT).show()
                }
                editTextTextPassword2.text.toString().count()<7 -> {
                    Toast.makeText(this,"Hasło powinno mieć więcej niż 6 znaków",Toast.LENGTH_SHORT).show()
                }
                editTextTextPassword2.text.toString().count()>128 -> {
                    Toast.makeText(this,"Hasło jest zbyt długie",Toast.LENGTH_SHORT).show()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(editTextTextEmailAddress2.text.toString()).matches() -> {
                    Toast.makeText(this,"Błędny adres email",Toast.LENGTH_SHORT).show()
                }
                else -> {

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextTextEmailAddress2.text.toString(),editTextTextPassword2.text.toString()).addOnCompleteListener {
                            task -> if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                        val db = FirebaseFirestore.getInstance()
                        val user: MutableMap<String,String> = HashMap()
                        user["nick"] = editTextTextPersonName.text.toString()
                        user["id"] = firebaseUser.uid
                        db.collection("users").add(user)

                            Toast.makeText(this,"Konto zostało założone w serwisie",Toast.LENGTH_SHORT).show()

                            val MainIntent = Intent(this, MainActivity::class.java)
                            startActivity(MainIntent)

                    }else{

                            Toast.makeText(this,"Wystąpił błąd podczas tworzenia konta",Toast.LENGTH_SHORT).show()
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