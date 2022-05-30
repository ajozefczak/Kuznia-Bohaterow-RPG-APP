package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dodaj_przedmiot.*
import kotlinx.android.synthetic.main.activity_historia_postaci.*
import kotlinx.android.synthetic.main.activity_logowanie.*
import java.util.HashMap



class DodajPrzedmiot : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_przedmiot)

        DodajPrzedmiotButtonDodaj.setOnClickListener {


            when {
                TextUtils.isEmpty(NazwaPrzedmiotuInput.text.toString()) -> {
                    Toast.makeText(this, R.string.WprowadzNazwePrzedmiotu, Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(KosztPrzedmiotuInput.text.toString()) -> {
                    Toast.makeText(this, R.string.WprowadzKosztPrzedmiotu, Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(OpisPrzedmiotuInput.text.toString()) -> {
                    Toast.makeText(this, R.string.WprowadzOpisPrzedmiotu, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val number = KosztPrzedmiotuInput.text.toString().toIntOrNull()
                    val isInteger = number != null
                    if(!isInteger){
                        Toast.makeText(this, R.string.WprowadzPoprawnyNumer, Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val idCharacter = intent.getStringExtra("id").toString()
                        DodajPrzedmiotButtonDodaj.isEnabled = false
                        val itemMutable: MutableMap<String, String> = HashMap()
                        itemMutable["userID"] = firebaseUser.uid
                        itemMutable["characterID"] = idCharacter
                        itemMutable["itemName"] = NazwaPrzedmiotuInput.text.toString()
                        itemMutable["itemValue"] = KosztPrzedmiotuInput.text.toString()
                        itemMutable["itemDescription"] = OpisPrzedmiotuInput.text.toString()
                        db.collection("equipment").add(itemMutable).addOnSuccessListener {
                            Toast.makeText(this, R.string.PoprawnieDodanoPrzedmiot, Toast.LENGTH_SHORT)
                                .show()
                            finish()
                            DodajPrzedmiotButtonDodaj.isEnabled = true

                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                R.string.WystapilBlad.toString() + e,
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