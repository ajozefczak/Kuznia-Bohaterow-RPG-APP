package com.example.kuznia_bohaterow_rpg_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_historia_postaci.*
import kotlinx.android.synthetic.main.activity_kalendarz.*
import java.util.HashMap

class HistoriaPostaci : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historia_postaci)

        val idCharacter = intent.getStringExtra("id").toString()


        FirebaseFirestore.getInstance().collection("history").whereEqualTo("characterID",idCharacter).get().addOnCompleteListener{task ->
            if(task.isSuccessful) {
                for (data in task.result) {
                    val history = data.data.getValue("history") as String
                    HistoriaEditTextTextMultiLineHistoria.setText(history)
                    }
                }
        }

        HistoriaButtonZapisz.setOnClickListener{
            HistoriaButtonZapisz.isEnabled = false
            val userHistory: MutableMap<String, String> = HashMap()
            userHistory["userID"] = firebaseUser.uid
            userHistory["characterID"] = idCharacter
            userHistory["history"] = HistoriaEditTextTextMultiLineHistoria.text.toString()
            db.collection("history").document(idCharacter).delete().addOnCompleteListener {
                db.collection("history").document(idCharacter).set(userHistory).addOnSuccessListener {
                    Toast.makeText(this, R.string.PoprawnieEdytowanoHistorie, Toast.LENGTH_SHORT).show()
                    HistoriaButtonZapisz.isEnabled = true
                }.addOnFailureListener { e ->
                    Toast.makeText(this, R.string.WystapilBlad.toString() + e, Toast.LENGTH_SHORT).show()
                    HistoriaButtonZapisz.isEnabled = true
                }
            }
        }

        HistoriaButtonCofnij.setOnClickListener {
            finish()
        }

    }
}