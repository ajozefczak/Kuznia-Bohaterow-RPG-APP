package com.example.kuznia_bohaterow_rpg_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dodaj_przedmiot.*
import kotlinx.android.synthetic.main.activity_dodaj_zaklecie.*
import java.util.HashMap


class DodajZaklecie : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_zaklecie)

        DodajZaklecieButtonDodaj.setOnClickListener{
            when {
                TextUtils.isEmpty(NazwaZaklecieInput.text.toString()) -> {
                    Toast.makeText(this, "Wprowadź nazwę zaklęcia", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(KosztZakleciaInput.text.toString()) -> {
                    Toast.makeText(this, "Wprowadź koszt zaklęcia", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(OpisZakleciaInput.text.toString()) -> {
                    Toast.makeText(this, "Wprowadź opis zaklęcia", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val number = KosztZakleciaInput.text.toString().toIntOrNull()
                    val isInteger = number != null
                    if(!isInteger){
                        Toast.makeText(this, "Wprowadź poprawny numer w koszcie", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val idCharacter = intent.getStringExtra("id").toString()
                        DodajZaklecieButtonDodaj.isEnabled = false
                        val spellMutable: MutableMap<String, String> = HashMap()
                        spellMutable["userID"] = firebaseUser.uid
                        spellMutable["characterID"] = idCharacter
                        spellMutable["spellName"] = NazwaZaklecieInput.text.toString()
                        spellMutable["spellCost"] = KosztZakleciaInput.text.toString()
                        spellMutable["spellDescription"] = OpisZakleciaInput.text.toString()
                        db.collection("spells").add(spellMutable).addOnSuccessListener {
                            Toast.makeText(this, "Poprawnie dodano zaklęcie.", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                            DodajZaklecieButtonDodaj.isEnabled = true

                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Wystąpił nieoczekiwany błąd: " + e,
                                Toast.LENGTH_SHORT
                            ).show()
                            DodajPrzedmiotButtonDodaj.isEnabled = true
                        }
                    }
                }
            }
        }
    }
}