package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_rozwoj_postaci.*
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
        var idTableJoin = ""

        codeID.text = codeJoin

        if(firebaseUser.uid.equals(gmID)){
            EditTableButton.visibility = View.VISIBLE
            StolyButtonDodajPostac.visibility = View.GONE
        }

        StolyButtonDodajPostac.setOnClickListener {
            val chooseCharacterIntent = Intent(this, ChooseCharacter::class.java)

            FirebaseFirestore.getInstance().collection("tables_joins").whereEqualTo("tableID", idTable).whereEqualTo("playerID", firebaseUser.uid).get().addOnCompleteListener { task ->
                if (task.isComplete) {
                    for (data in task.result) {
                        idTableJoin = data.id
                        chooseCharacterIntent.putExtra("tableID", idTableJoin)
                        startActivity(chooseCharacterIntent)
                    }
                }
            }
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

                var tempNick = firebaseUser.uid
                FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
                    if (task2.isComplete) {
                        for (data2 in task2.result) {
                            tempNick = data2["nick"].toString()
                            charactersheet["id"] = firebaseUser.uid
                            charactersheet["message"] = editTextTextPersonName5.text.toString()
                            charactersheet["table"] = idTable
                            charactersheet["nick"] = tempNick
                            charactersheet["date"] = current.toString()

                            db.collection("chat").add(charactersheet).addOnSuccessListener {
                                Toast.makeText(this, "Wiadomość została wysłana", Toast.LENGTH_SHORT).show()
                                StolyButtonWyslijWiadomosc.isEnabled = true
                                editTextTextPersonName5.text.clear()
                            }.addOnFailureListener { e ->
                                Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                                StolyButtonWyslijWiadomosc.isEnabled = true
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(this, "Wprowadź wiadomość", Toast.LENGTH_SHORT).show()
                StolyButtonWyslijWiadomosc.isEnabled = true
            }
        }

        StolyButtonD4.setOnClickListener{
            StolyButtonD4.isEnabled = false
            val pepegaValue = (1..4).random()

            val charactersheet: MutableMap<String, String> = HashMap()
            val current = LocalDateTime.now()

            var tempNick = firebaseUser.uid
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
                if (task2.isComplete) {
                    for (data2 in task2.result) {
                        tempNick = data2["nick"].toString()
                        charactersheet["id"] = firebaseUser.uid
                        charactersheet["message"] = "Użytkownik rzucił d4 i wylosował " + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                            StolyButtonWyslijWiadomosc.isEnabled = true
                        }
                    }
                }
            }
            StolyButtonD4.isEnabled = true
        }

        StolyButtonD6.setOnClickListener{
            StolyButtonD6.isEnabled = false
            val pepegaValue = (1..6).random()

            val charactersheet: MutableMap<String, String> = HashMap()
            val current = LocalDateTime.now()

            var tempNick = firebaseUser.uid
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
                if (task2.isComplete) {
                    for (data2 in task2.result) {
                        tempNick = data2["nick"].toString()
                        charactersheet["id"] = firebaseUser.uid
                        charactersheet["message"] = "Użytkownik rzucił d6 i wylosował " + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                            StolyButtonWyslijWiadomosc.isEnabled = true
                        }
                    }
                }
            }
            StolyButtonD6.isEnabled = true
        }

        StolyButtonD8.setOnClickListener{
            StolyButtonD8.isEnabled = false
            val pepegaValue = (1..8).random()

            val charactersheet: MutableMap<String, String> = HashMap()
            val current = LocalDateTime.now()

            var tempNick = firebaseUser.uid
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
                if (task2.isComplete) {
                    for (data2 in task2.result) {
                        tempNick = data2["nick"].toString()
                        charactersheet["id"] = firebaseUser.uid
                        charactersheet["message"] = "Użytkownik rzucił d8 i wylosował " + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                            StolyButtonWyslijWiadomosc.isEnabled = true
                        }
                    }
                }
            }
            StolyButtonD8.isEnabled = true
        }

        StolyButtonD10.setOnClickListener{
            StolyButtonD10.isEnabled = false
            val pepegaValue = (1..10).random()

            val charactersheet: MutableMap<String, String> = HashMap()
            val current = LocalDateTime.now()

            var tempNick = firebaseUser.uid
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
                if (task2.isComplete) {
                    for (data2 in task2.result) {
                        tempNick = data2["nick"].toString()
                        charactersheet["id"] = firebaseUser.uid
                        charactersheet["message"] = "Użytkownik rzucił d10 i wylosował " + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                            StolyButtonWyslijWiadomosc.isEnabled = true
                        }
                    }
                }
            }
            StolyButtonD10.isEnabled = true
        }

        StolyButtonD12.setOnClickListener{
            StolyButtonD12.isEnabled = false
            val pepegaValue = (1..12).random()

            val charactersheet: MutableMap<String, String> = HashMap()
            val current = LocalDateTime.now()

            var tempNick = firebaseUser.uid
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
                if (task2.isComplete) {
                    for (data2 in task2.result) {
                        tempNick = data2["nick"].toString()
                        charactersheet["id"] = firebaseUser.uid
                        charactersheet["message"] = "Użytkownik rzucił d12 i wylosował " + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                            StolyButtonWyslijWiadomosc.isEnabled = true
                        }
                    }
                }
            }
            StolyButtonD12.isEnabled = true
        }

        StolyButtonD20.setOnClickListener{
            StolyButtonD20.isEnabled = false
            val pepegaValue = (1..20).random()

            val charactersheet: MutableMap<String, String> = HashMap()
            val current = LocalDateTime.now()

            var tempNick = firebaseUser.uid
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
                if (task2.isComplete) {
                    for (data2 in task2.result) {
                        tempNick = data2["nick"].toString()
                        charactersheet["id"] = firebaseUser.uid
                        charactersheet["message"] = "Użytkownik rzucił d20 i wylosował " + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                            StolyButtonWyslijWiadomosc.isEnabled = true
                        }
                    }
                }
            }
            StolyButtonD20.isEnabled = true
        }

        StolyButtonD100.setOnClickListener{
            StolyButtonD100.isEnabled = false
            val pepegaValue = (1..100).random()

            val charactersheet: MutableMap<String, String> = HashMap()
            val current = LocalDateTime.now()

            var tempNick = firebaseUser.uid
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task2 ->
                if (task2.isComplete) {
                    for (data2 in task2.result) {
                        tempNick = data2["nick"].toString()
                        charactersheet["id"] = firebaseUser.uid
                        charactersheet["message"] = "Użytkownik rzucił d100 i wylosował " + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                            StolyButtonWyslijWiadomosc.isEnabled = true
                        }
                    }
                }
            }
            StolyButtonD100.isEnabled = true
        }

        archivum.setOnClickListener {
            archivum.isEnabled = false
            val archivumIntent = Intent(this, archiwum::class.java)
            archivumIntent.putExtra("tableID",idTable)
            startActivity(archivumIntent)
            archivum.isEnabled = true
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

                    var firstTenMsg = chatMessages.takeLast(15).toMutableList()

                    val myListChatAdapter = MyListChatMessageAdapter(this,firstTenMsg)
                    chatlist.adapter = myListChatAdapter
                }
            }
        }

        FirebaseFirestore.getInstance().collection("tables_joins").whereEqualTo("tableID", idTable).get().addOnCompleteListener { task ->
            if (task.isComplete) {
                for (data in task.result) {

                    var tempPlayerID = data["playerID"].toString()
                    var tempCharID = data["characterID"].toString()
                    var tempNick = "Nieznany"
                    var tempCharName = "Brak wybranej postaci"

                    if(tempCharID != "brak") {
                        tempCharName = data["characterName"].toString()
                    }

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

                        var firstTenMsg = chatMessages.takeLast(15).toMutableList()

                        val myListChatAdapter = MyListChatMessageAdapter(this,firstTenMsg)
                        chatlist.adapter = myListChatAdapter
                    }
                }
            }
            val players: MutableList<PlayerOnList> = mutableListOf()
            FirebaseFirestore.getInstance().collection("tables_joins").whereEqualTo("tableID", idTable).get().addOnCompleteListener { task ->
                if (task.isComplete) {
                    for (data in task.result) {

                        var tempPlayerID = data["playerID"].toString()
                        var tempCharID = data["characterID"].toString()
                        var tempNick = "Nieznany"
                        var tempCharName = "Brak wybranej postaci"

                        if(tempCharID != "brak") {
                            tempCharName = data["characterName"].toString()
                        }

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
        }.also { runnable = it },delay.toLong())
        super.onResume()
    }

}