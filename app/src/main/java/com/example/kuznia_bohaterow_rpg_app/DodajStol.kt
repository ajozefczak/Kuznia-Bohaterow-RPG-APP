package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dodaj_przedmiot.*
import kotlinx.android.synthetic.main.activity_dodaj_stol.*
import java.util.HashMap

class DodajStol : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_stol)

        DodajStolButton.setOnClickListener {


            when {
                TextUtils.isEmpty(NazwaStoluInput.text.toString()) -> {
                    Toast.makeText(this, "Wprowadź nazwę stolu", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(OpisStoluInput.text.toString()) -> {
                    Toast.makeText(this, "Wprowadź opis stolu", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    DodajStolButton.isEnabled = false
                        val itemMutable: MutableMap<String, String> = HashMap()
                        val itemMutable2: MutableMap<String, String> = HashMap()
                        itemMutable["gmID"] = firebaseUser.uid
                        itemMutable["tableName"] = NazwaStoluInput.text.toString()
                        itemMutable["tableDesc"] = OpisStoluInput.text.toString()
                        itemMutable["joinCode"] = generateCode()
                        itemMutable["joinCode"] = generateCode()
                        itemMutable["joinCode"] = generateCode()
                        db.collection("tables").add(itemMutable).addOnSuccessListener {
                            Toast.makeText(this, "Poprawnie dodano stół.", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                            DodajStolButton.isEnabled = true

                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Wystąpił nieoczekiwany błąd: " + e,
                                Toast.LENGTH_SHORT
                            ).show()
                            DodajStolButton.isEnabled = true
                        }
                }
            }
        }
    }
    fun generateCode() : String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }
}