package com.example.kuznia_bohaterow_rpg_app

import android.accounts.AccountsException
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.activity_rejestracja.*
import java.lang.Exception


class Rejestracja : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejestracja)

        initListeners()
        setupHyperlink()

        // Code to register pepega acc

        var errorMessage = "Wystąpił błąd w następujących polach:\n"
        var tockenAcc = true;

        RButtonRejestracja.setOnClickListener{

            if(TextUtils.isEmpty(editTextTextPersonName.text.toString().trim { it <= ' ' })) {
                errorMessage = errorMessage + "Wprowadź nick\n"
                tockenAcc = false
            }
            /*if(TextUtils.isEmpty(editTextTextEmailAddress2.text.toString())){
                errorMessage = errorMessage + "Wprowadź adres email\n"
                tockenAcc = false
            }*/
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextTextEmailAddress2.text.toString()).matches()){
                errorMessage = errorMessage + "Błędny adres email\n"
                tockenAcc = false
            }
            if(TextUtils.isEmpty(editTextTextPassword2.text.toString())){
                errorMessage = errorMessage + "Wprowadź hasło\n"
                tockenAcc = false
            }
            if(TextUtils.isEmpty(editTextTextPassword3.text.toString())){
                errorMessage = errorMessage + "Wprowadź ponownie haslo\n"
                tockenAcc = false
            }
            if(!editTextTextPassword2.text.toString().equals(editTextTextPassword3.text.toString())){
                errorMessage = errorMessage + "Hasła nie są takie same\n"
                tockenAcc = false
            }
            if(!checkBox.isChecked){
                errorMessage = errorMessage + "W celu utworzenia konta wymagana jest akceptacja regulaminu\n"
                tockenAcc = false
            }
            if(editTextTextPassword2.text.toString().count()<7){
                errorMessage = errorMessage + "Hasło powinno mieć więcej niż 6 znaków\n"
                tockenAcc = false
            }
            if(editTextTextPassword2.text.toString().count()>128){
                errorMessage = errorMessage + "Hasło jest zbyt długie\n"
                tockenAcc = false
            }
            if(tockenAcc==true) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    editTextTextEmailAddress2.text.toString(),
                    editTextTextPassword2.text.toString()
                ).addOnSuccessListener {
                    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

                    val db = FirebaseFirestore.getInstance()
                    val user: MutableMap<String, String> = HashMap()
                    user["nick"] = editTextTextPersonName.text.toString()
                    user["id"] = firebaseUser.uid
                    db.collection("users").add(user)

                    Toast.makeText(this, "Konto zostało założone w serwisie", Toast.LENGTH_SHORT).show()

                    val MainIntent = Intent(this, MainActivity::class.java)
                    startActivity(MainIntent)
                    finish()

                }.addOnFailureListener { e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

                    // NIE USUWAC TEGO CO JEST NIZEJ W KOMENTARZU

                    /*
                        var exep = (e.cause as FirebaseAuthException?)!!.errorCode
                        Toast.makeText(this, exep, Toast.LENGTH_SHORT).show()
                    if(exep=="auth/email-already-exists")
                        Toast.makeText(this, "pepega", Toast.LENGTH_SHORT).show()
                    else{
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }*/

                }
            }else{
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                tockenAcc=true
                errorMessage = "Wystąpił błąd w następujących polach: "
            }
        }

    }

    fun setupHyperlink() {
        val linkTextView = findViewById<TextView>(R.id.RButtonRegulamin)
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextView.setLinkTextColor(Color.BLUE)
    }


    private fun initListeners() {
        val RButtonLogowanie = findViewById<Button>(R.id.RButtonLogowanie)
        RButtonLogowanie.setOnClickListener(RButtonLogowanieListener)

        val RButtonRegulamin = findViewById<Button>(R.id.RButtonRegulamin)
        RButtonRegulamin.setOnClickListener(RButtonRegulaminListener)
    }

    private val RButtonLogowanieListener = View.OnClickListener { callLogowanieActivity() }
    private val RButtonRegulaminListener = View.OnClickListener { callRegulaminActivity() }

    private fun callLogowanieActivity() {
        val LogowanieIntent = Intent(this, Logowanie::class.java)
        startActivity(LogowanieIntent)
        finish()
    }

    private fun callRegulaminActivity() {
        val RegulaminIntent = Intent(this, Regulamin::class.java)
        startActivity(RegulaminIntent)
        finish()
    }

    private fun registerAcc(){

    }

}