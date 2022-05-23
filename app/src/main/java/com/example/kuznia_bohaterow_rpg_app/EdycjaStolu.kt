package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edycja_stolu.*
import kotlinx.android.synthetic.main.activity_ekran_postaci.*
import kotlinx.android.synthetic.main.activity_lista_postaci.*
import kotlinx.android.synthetic.main.activity_stoly.*

class EdycjaStolu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edycja_stolu)
        val idTable = intent.getStringExtra("tableID").toString()

        EdycjaStoluButtonCofnij.setOnClickListener {
            EdycjaStoluButtonCofnij.isEnabled = false
            EdycjaStoluButtonCofnij.isEnabled = true
            finish()
        }

        EdycjaStoluButtonUsunStol.setOnClickListener {
            EdycjaStoluButtonUsunStol.isEnabled = false


            FirebaseFirestore.getInstance().collection("tables").document(idTable).delete().addOnSuccessListener {
                Toast.makeText(this, "Poprawnie usunięto stół", Toast.LENGTH_SHORT).show()
                val listaStolowIntent = Intent(this, ListaStoly::class.java)
                startActivity(listaStolowIntent)
                finish()
                EdycjaStoluButtonUsunStol.isEnabled = true
            }.addOnFailureListener{
                Toast.makeText(this, "Wystąpił błąd przy usuwaniu stołu!", Toast.LENGTH_SHORT).show()
                EdycjaStoluButtonUsunStol.isEnabled = true
            }
        }

        EdycjaStoluButtonZmienNazwe.setOnClickListener {
            EdycjaStoluButtonZmienNazwe.isEnabled = false
            FirebaseFirestore.getInstance().collection("tables").document(idTable).update(mapOf("tableName" to EditTableNameText.text.toString()))
            Toast.makeText(this, "Poprawnie zmieniono nazwę stołu.", Toast.LENGTH_SHORT).show()
            EdycjaStoluButtonZmienNazwe.isEnabled = true
        }

        EdycjaStoluButtonZmienOpis.setOnClickListener {
            EdycjaStoluButtonZmienOpis.isEnabled = false
            FirebaseFirestore.getInstance().collection("tables").document(idTable).update(mapOf("tableDesc" to EditTableTextDesc.text.toString()))
            Toast.makeText(this, "Poprawnie zmieniono opis stołu.", Toast.LENGTH_SHORT).show()
            EdycjaStoluButtonZmienOpis.isEnabled = true
        }

        FirebaseFirestore.getInstance().collection("tables").document(idTable).get().addOnCompleteListener { task->
            if(task.isComplete){

                EditTableNameText.setText(task.result["tableName"].toString())
                EditTableTextDesc.setText(task.result["tableDesc"].toString())
            }
        }

        val playersKick: MutableList<PlayerKickOnList> = mutableListOf()
        FirebaseFirestore.getInstance().collection("tables_joins").whereEqualTo("tableID", idTable).get().addOnCompleteListener { task ->
            if (task.isComplete) {
                for (data in task.result) {

                    var tempPlayerID = data["playerID"].toString()
                    var tempJoinID = data.id

                    FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", tempPlayerID).get().addOnCompleteListener { task2 ->
                        if (task2.isComplete) {
                            for(data2 in task2.result){
                                var tempNick = data2["nick"].toString()
                                var playertemp = PlayerKickOnList(
                                    tempNick,
                                    tempPlayerID,
                                    tempJoinID
                                )
                                playersKick.add(playertemp)
                            }
                            playersKick.sortBy { it.nick }
                            val myListAdapter = MyListKickAdapter(this, playersKick)
                            ETListOfUsers.adapter = myListAdapter
                        }
                    }
                }
            }
        }
    }
    override fun onRestart() {
        super.onRestart()
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}