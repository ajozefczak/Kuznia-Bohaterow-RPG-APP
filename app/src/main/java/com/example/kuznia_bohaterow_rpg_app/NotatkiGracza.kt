package com.example.kuznia_bohaterow_rpg_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_notatki_gracza.*
import java.util.HashMap

class NotatkiGracza : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notatki_gracza)

        val idCharacter = intent.getStringExtra("id").toString()


        FirebaseFirestore.getInstance().collection("notes").whereEqualTo("characterID",idCharacter).get().addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                for (data in task.result) {
                    val notes = data.data.getValue("note") as String
                    NGEditTextTextMultiLineNotatkiGracza.setText(notes)
                }
            }
        }

        NotatkiButtonZapisz.setOnClickListener{
            NotatkiButtonZapisz.isEnabled = false
            val userNotes: MutableMap<String, String> = HashMap()
            userNotes["userID"] = firebaseUser.uid
            userNotes["characterID"] = idCharacter
            userNotes["note"] = NGEditTextTextMultiLineNotatkiGracza.text.toString()
            db.collection("notes").document(idCharacter).delete().addOnCompleteListener {
                db.collection("notes").document(idCharacter).set(userNotes).addOnSuccessListener {
                    Toast.makeText(this, R.string.PoprawnieEdytowanoNotatke, Toast.LENGTH_SHORT).show()
                    NotatkiButtonZapisz.isEnabled = true
                }.addOnFailureListener { e ->
                    Toast.makeText(this, R.string.WystapilBlad.toString() + e, Toast.LENGTH_SHORT).show()
                    NotatkiButtonZapisz.isEnabled = true
                }
            }
        }

        NotatkiButtonCofnij.setOnClickListener {
            finish()
        }

    }
}