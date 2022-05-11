package com.example.kuznia_bohaterow_rpg_app

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_ekran_postaci.*
import kotlinx.android.synthetic.main.activity_kalendarz.*
import kotlinx.android.synthetic.main.activity_lista_postaci.*
import kotlinx.android.synthetic.main.activity_stoly.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Stoly : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 3000

    @RequiresApi(Build.VERSION_CODES.O)
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

        StolyButtonWyslijWiadomosc.setOnClickListener{
            StolyButtonWyslijWiadomosc.isEnabled = false

            if(!editTextTextPersonName5.text.isEmpty()){

                val charactersheet: MutableMap<String, String> = HashMap()

                val current = LocalDateTime.now()
                /*val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatedCurrent: String = current.format(formatter)*/

                charactersheet["id"] = firebaseUser.uid
                charactersheet["message"] = editTextTextPersonName5.text.toString()
                charactersheet["table"] = idTable
                charactersheet["nick"] = firebaseUser.uid
                charactersheet["date"] = current.toString()

                db.collection("chat").add(charactersheet).addOnSuccessListener {
                        Toast.makeText(this, "Wiadomość została wysłana", Toast.LENGTH_SHORT).show()
                    StolyButtonWyslijWiadomosc.isEnabled = true
                    editTextTextPersonName5.text.clear()
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                    StolyButtonWyslijWiadomosc.isEnabled = true
                    }
            }else{
                Toast.makeText(this, "Wprowadź wiadomość", Toast.LENGTH_SHORT).show()
                StolyButtonWyslijWiadomosc.isEnabled = true
            }
        }

        val players: MutableList<PlayerOnList> = mutableListOf()
        val chatMessages: MutableList<MessagesOnList> = mutableListOf()

        FirebaseFirestore.getInstance().collection("chat").whereEqualTo("table",idTable).get().addOnCompleteListener{ task ->
            if(task.isComplete){
                for(data in task.result){
                    var nick = data["nick"].toString()
                    var message = data["message"].toString()
                    var date = data["date"].toString()


                    var tempChatMessage = MessagesOnList(nick,message,date)
                    chatMessages.add(tempChatMessage)

                    chatMessages.sortBy { it.date }

                    val myListChatAdapter = MyListChatMessageAdapter(this,chatMessages)
                    chatlist.adapter = myListChatAdapter
                }
            }
        }

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

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())

            val chatMessages: MutableList<MessagesOnList> = mutableListOf()
            val idTable = intent.getStringExtra("tableID").toString()

            FirebaseFirestore.getInstance().collection("chat").whereEqualTo("table",idTable).get().addOnCompleteListener{ task ->
                if(task.isComplete){
                    for(data in task.result){
                        var nick = data["nick"].toString()
                        var message = data["message"].toString()
                        var date = data["date"].toString()


                        var tempChatMessage = MessagesOnList(nick,message,date)
                        chatMessages.add(tempChatMessage)

                        chatMessages.sortBy { it.date }

                        val myListChatAdapter = MyListChatMessageAdapter(this,chatMessages)
                        chatlist.adapter = myListChatAdapter
                    }
                }
            }

        }.also { runnable = it },delay.toLong())
        super.onResume()
    }

}