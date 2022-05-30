package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_choose_character.*
import kotlinx.android.synthetic.main.activity_kalendarz.*
import kotlinx.android.synthetic.main.activity_lista_postaci.*
import kotlinx.android.synthetic.main.activity_stoly.*

class ChooseCharacter : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onRestart() {
        super.onRestart()
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_character)

        val characters2: MutableList<CharacterOnList> = mutableListOf()
        val idTable = intent.getStringExtra("tableID").toString()

        FirebaseFirestore.getInstance().collection("charactersheet").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
            if (task2.isSuccessful) {
                for (data in task2.result) {
                    var tempCharName2 = data["name"].toString()
                    var tempOccup2 = data["job"].toString()
                    var tempAvatars2 = data["imgURL"].toString()
                    var tempIds2 = data.id

                    var chartemp2 = CharacterOnList(tempCharName2, tempOccup2, tempAvatars2, tempIds2)
                    characters2.add(chartemp2)
                }

                characters2.sortBy {it.firstName}
                val myListAdapter = MyListChooseAdapter(this,characters2)
                characterChooseList.adapter = myListAdapter

                characterChooseList.setOnItemClickListener(){adapterView, view, position, id ->
                    db.collection("tables_joins").document(idTable).update("characterID", characters2[position].id, "characterName", characters2[position].firstName).addOnSuccessListener  {
                        Toast.makeText(this, R.string.PoprawnieWybranoPostac, Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, R.string.WystapilBlad.toString() + e, Toast.LENGTH_SHORT).show()
                    }
                }

                ChooseButtonCofnij.setOnClickListener {
                    ChooseButtonCofnij.isEnabled = false
                    finish()
                    ChooseButtonCofnij.isEnabled = true
                }


            }
        }
    }
}