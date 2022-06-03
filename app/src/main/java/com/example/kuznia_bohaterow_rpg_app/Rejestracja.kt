package com.example.kuznia_bohaterow_rpg_app

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

        var errorMessage = getString(R.string.WystapilBlad) + "\n";
        var tockenAcc = true;

        RButtonRejestracja.setOnClickListener{

            RButtonRejestracja.isEnabled = false

            if(TextUtils.isEmpty(editTextTextPersonName.text.toString().trim { it <= ' ' })) {
                errorMessage = errorMessage + getString(R.string.WprowadzNick) + "\n"
                tockenAcc = false
            }
            /*if(TextUtils.isEmpty(editTextTextEmailAddress2.text.toString())){
                errorMessage = errorMessage + "Wprowadź adres email\n"
                tockenAcc = false
            }*/
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextTextEmailAddress2.text.toString()).matches()){
                errorMessage = errorMessage + getString(R.string.BlednyAdres) + "\n"
                tockenAcc = false
            }
            if(TextUtils.isEmpty(editTextTextPassword2.text.toString())){
                errorMessage = errorMessage + getString(R.string.WprowadzHaslo) + "\n"
                tockenAcc = false
            }
            if(TextUtils.isEmpty(editTextTextPassword3.text.toString())){
                errorMessage = errorMessage + getString(R.string.WprowadzPonownieHaslo) + "\n"
                tockenAcc = false
            }
            if(!editTextTextPassword2.text.toString().equals(editTextTextPassword3.text.toString())){
                errorMessage = errorMessage + getString(R.string.HaslaTakieSame) + "\n"
                tockenAcc = false
            }
            if(!checkBox.isChecked){
                errorMessage = errorMessage + getString(R.string.WCelu) + "\n"
                tockenAcc = false
            }
            if(editTextTextPassword2.text.toString().count()<7){
                errorMessage = errorMessage + getString(R.string.HaslaWieksze) + "\n"
                tockenAcc = false
            }
            if(editTextTextPassword2.text.toString().count()>128){
                errorMessage = errorMessage + getString(R.string.HasloZbyt) + "\n"
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

                    Toast.makeText(this, getString(R.string.ZalozoneKonto), Toast.LENGTH_SHORT).show()

                    val MainIntent = Intent(this, MainActivity::class.java)
                    startActivity(MainIntent)
                    RButtonRejestracja.isEnabled = true
                    finish()
                }.addOnFailureListener { e ->
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    RButtonRejestracja.isEnabled = true

                }
            }else{
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                tockenAcc=true
                errorMessage = getString(R.string.WystapilBlad)
                RButtonRejestracja.isEnabled = true
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
        RButtonLogowanie.isEnabled = false
        val LogowanieIntent = Intent(this, Logowanie::class.java)
        startActivity(LogowanieIntent)
        RButtonLogowanie.isEnabled = true
        finish()
    }

    private fun callRegulaminActivity() {
        RButtonRegulamin.isEnabled = false
        val RegulaminIntent = Intent(this, Regulamin::class.java)
        startActivity(RegulaminIntent)
        RButtonRegulamin.isEnabled = true
        finish()
    }

    private fun registerAcc(){

    }

}