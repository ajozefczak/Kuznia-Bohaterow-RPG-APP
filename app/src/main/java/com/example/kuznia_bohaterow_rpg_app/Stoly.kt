package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_ekran_postaci.*
import kotlinx.android.synthetic.main.activity_lista_postaci.*
import kotlinx.android.synthetic.main.activity_stoly.*

class Stoly : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stoly)

        val idTable = intent.getStringExtra("tableID").toString()
        var gmID = intent.getStringExtra("gmID").toString()
        var codeJoin = intent.getStringExtra("joinCode").toString()

        codeID.text = codeJoin

        if(firebaseUser.uid.equals(gmID)){
            EditTableButton.visibility = View.VISIBLE
            StolyButtonDodajPostac.visibility = View.GONE
        }

        StolyButtonCofnij.setOnClickListener {
            finish()
        }

        val players: MutableList<PlayerOnList> = mutableListOf()

        FirebaseFirestore.getInstance().collection("tables_joins").whereEqualTo("tableID", idTable).get().addOnCompleteListener { task ->
            if (task.isComplete) {
                for (data in task.result) {
                    var tempPlayerID = data["playerID"].toString()
                    var tempCharID = data["characterID"].toString()
                    var tempCharName = "Brak przypisanej postaci"
                    var tempNick = "Nieznany"

                    FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", tempPlayerID).get().addOnCompleteListener { task2 ->
                        if (task2.isComplete) {
                            for(data2 in task2.result){
                                tempNick = data2["nick"].toString()
                                var playertemp = PlayerOnList(
                                    tempNick,
                                    tempCharName,
                                    tempCharID,
                                    tempPlayerID
                                )
                                players.add(playertemp)
                            }
                            players.sortBy { it.nick }
                            val myListAdapter = MyListPlayerAdapter(this, players)
                            playerList.adapter = myListAdapter
                        }
                    }
                }

            }
        }
    }
}