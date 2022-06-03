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

class Stoly : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 3000
    var shouldExecuteOnResume = true
    var colorR = "255"
    var colorG = "255"
    var colorB = "255"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stoly)

        shouldExecuteOnResume = true

        val idTable = intent.getStringExtra("tableID").toString()
        var gmID = intent.getStringExtra("gmID").toString()
        var codeJoin = intent.getStringExtra("joinCode").toString()
        var idTableJoin = ""

        codeID.text = codeJoin

        if(firebaseUser.uid.equals(gmID)){
            EditTableButton.visibility = View.VISIBLE
            StolyButtonDodajPostac.visibility = View.GONE
        }

        FirebaseFirestore.getInstance().collection("tables_joins").whereEqualTo("tableID", idTable).whereEqualTo("playerID", firebaseUser.uid).get().addOnCompleteListener { task ->
            if (task.isComplete) {
                for (data in task.result) {
                    colorR = data["colorR"].toString()
                    colorG = data["colorG"].toString()
                    colorB = data["colorB"].toString()
                }
            }
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

        EditTableButton.setOnClickListener{
            val editTable = Intent(this, EdycjaStolu::class.java)
            val idTable = intent.getStringExtra("tableID").toString()
            editTable.putExtra("tableID",idTable);
            startActivity(editTable);
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

                            charactersheet["colorR"] = colorR
                            charactersheet["colorG"] = colorG
                            charactersheet["colorB"] = colorB


                            db.collection("chat").add(charactersheet).addOnSuccessListener {
                                StolyButtonWyslijWiadomosc.isEnabled = true
                                editTextTextPersonName5.text.clear()
                            }.addOnFailureListener { e ->
                                Toast.makeText(this, getString(R.string.WystapilBlad) + e, Toast.LENGTH_SHORT).show()
                                StolyButtonWyslijWiadomosc.isEnabled = true
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(this, R.string.WprowadzWiadomosc, Toast.LENGTH_SHORT).show()
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
                        charactersheet["message"] = getString(R.string.d4) + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()
                        charactersheet["colorR"] = colorR
                        charactersheet["colorG"] = colorG
                        charactersheet["colorB"] = colorB


                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, getString(R.string.WystapilBlad) + e, Toast.LENGTH_SHORT).show()
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
                        charactersheet["message"] = getString(R.string.d6) + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()
                        charactersheet["colorR"] = colorR
                        charactersheet["colorG"] = colorG
                        charactersheet["colorB"] = colorB

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, getString(R.string.WystapilBlad) + e, Toast.LENGTH_SHORT).show()
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
                        charactersheet["message"] = getString(R.string.d8) + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()
                        charactersheet["colorR"] = colorR
                        charactersheet["colorG"] = colorG
                        charactersheet["colorB"] = colorB

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, getString(R.string.WystapilBlad) + e, Toast.LENGTH_SHORT).show()
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
                        charactersheet["message"] = getString(R.string.d10) + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()
                        charactersheet["colorR"] = colorR
                        charactersheet["colorG"] = colorG
                        charactersheet["colorB"] = colorB

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, getString(R.string.WystapilBlad) + e, Toast.LENGTH_SHORT).show()
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
                        charactersheet["message"] = getString(R.string.d12) + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()
                        charactersheet["colorR"] = colorR
                        charactersheet["colorG"] = colorG
                        charactersheet["colorB"] = colorB

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, getString(R.string.WystapilBlad) + e, Toast.LENGTH_SHORT).show()
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
                        charactersheet["message"] = getString(R.string.d20) + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()
                        charactersheet["colorR"] = colorR
                        charactersheet["colorG"] = colorG
                        charactersheet["colorB"] = colorB

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, getString(R.string.WystapilBlad) + e, Toast.LENGTH_SHORT).show()
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
                        charactersheet["message"] = getString(R.string.d100) + pepegaValue + "."
                        charactersheet["table"] = idTable
                        charactersheet["nick"] = tempNick
                        charactersheet["date"] = current.toString()
                        charactersheet["colorR"] = colorR
                        charactersheet["colorG"] = colorG
                        charactersheet["colorB"] = colorB

                        db.collection("chat").add(charactersheet).addOnSuccessListener {
                            StolyButtonWyslijWiadomosc.isEnabled = true
                            editTextTextPersonName5.text.clear()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, getString(R.string.WystapilBlad) + e, Toast.LENGTH_SHORT).show()
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

                    var msgColorR = data["colorR"].toString()
                    var msgColorG = data["colorG"].toString()
                    var msgColorB = data["colorB"].toString()


                    var tempChatMessage = MessagesOnList(nick,message,date,msgColorR,msgColorG,msgColorB)
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
                    var tempCharName = getString(R.string.BrakWybranejPostaci)

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



                            playerList.setOnItemClickListener(){adapterView, view, position, id ->
                                if(players[position].charname != getString(R.string.BrakWybranejPostaci)){
                                    val intent = Intent(this,CheckCharacterOnList::class.java)
                                    intent.putExtra("id",players[position].chID)
                                    startActivity(intent)
                                    finish()
                                }
                                else {
                                    Toast.makeText(this, R.string.GraczNieWybral, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    override fun onRestart() {
        super.onRestart()
        val idTable = intent.getStringExtra("tableID").toString()
        FirebaseFirestore.getInstance().collection("tables").document(idTable).get().addOnSuccessListener(){ task ->
            if(!task.exists()){
                finish()
            }
        }
    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())

            if(shouldExecuteOnResume) {
                var idTable = intent.getStringExtra("tableID").toString()
                var gmID = intent.getStringExtra("gmID").toString()
                var temp = false

                FirebaseFirestore.getInstance().collection("tables_joins").whereEqualTo("tableID",idTable).whereEqualTo("playerID", firebaseUser.uid).get().addOnSuccessListener{ task ->
                    if(task.isEmpty && gmID != firebaseUser.uid) {
                            Toast.makeText(this, R.string.ZostalesWyrzucony, Toast.LENGTH_SHORT).show()
                            finish()
                            shouldExecuteOnResume = false
                    }
                }

                FirebaseFirestore.getInstance().collection("tables").document(idTable).get().addOnCompleteListener{ task ->
                    if(!task.result.exists()) {
                        Toast.makeText(this, R.string.StolUsuniety, Toast.LENGTH_SHORT).show()
                        finish()
                        shouldExecuteOnResume = false
                    }
                }

                val chatMessages: MutableList<MessagesOnList> = mutableListOf()

                FirebaseFirestore.getInstance().collection("chat").whereEqualTo("table",idTable).get().addOnCompleteListener{ task ->
                    if(task.isComplete){
                        for(data in task.result){
                            var nick = data["nick"].toString()
                            var message = data["message"].toString()
                            var date = data["date"].toString()

                            var msgColorR = data["colorR"].toString()
                            var msgColorG = data["colorG"].toString()
                            var msgColorB = data["colorB"].toString()


                            var tempChatMessage = MessagesOnList(nick,message,date,msgColorR,msgColorG,msgColorB)
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
                    if (!task.result.isEmpty) {
                        for (data in task.result) {
                            var tempPlayerID = data["playerID"].toString()
                            var tempCharID = data["characterID"].toString()
                            var tempNick = "Nieznany"
                            var tempCharName = getString(R.string.BrakWybranejPostaci)
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

                                    playerList.setOnItemClickListener(){adapterView, view, position, id ->
                                        if(players[position].charname != getString(R.string.BrakWybranejPostaci)){
                                            val intent = Intent(this,CheckCharacterOnList::class.java)
                                            intent.putExtra("id",players[position].chID)
                                            startActivity(intent)
                                            finish()
                                        }
                                        else {
                                            Toast.makeText(this, R.string.GraczNieWybral, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        players.clear()
                        val myListAdapter = MyListPlayerAdapter(this, players)
                        playerList.adapter = myListAdapter
                    }
                }
            }

        }.also { runnable = it },delay.toLong())
        super.onResume()
    }

}