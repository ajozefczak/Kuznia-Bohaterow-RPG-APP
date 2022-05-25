package com.example.kuznia_bohaterow_rpg_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_archiwum.*
import kotlinx.android.synthetic.main.activity_stoly.*

class archiwum : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archiwum)


        val chatMessages: MutableList<MessagesOnList> = mutableListOf()
        val idTable = intent.getStringExtra("tableID").toString()
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

                    val myListChatAdapter = MyListChatArchiveAdapter(this,chatMessages)
                    tableList.adapter = myListChatAdapter
                }
            }
        }

        SearchButton.setOnClickListener {
            chatMessages.clear()
            val myListChatAdapter = MyListChatArchiveAdapter(this,chatMessages)
            tableList.adapter = myListChatAdapter

            var searchedText = SearchBox.text
            FirebaseFirestore.getInstance().collection("chat").whereEqualTo("table",idTable).get().addOnCompleteListener { task ->
                if (task.isComplete) {
                    for (data in task.result) {
                        if(data["message"].toString().lowercase().contains(searchedText)){
                            var nick = data["nick"].toString()
                            var message = data["message"].toString()
                            var date = data["date"].toString()

                            var msgColorR = data["colorR"].toString()
                            var msgColorG = data["colorG"].toString()
                            var msgColorB = data["colorB"].toString()


                            var tempChatMessage = MessagesOnList(nick,message,date,msgColorR,msgColorG,msgColorB)
                            chatMessages.add(tempChatMessage)

                            chatMessages.sortBy { it.date }

                            val myListChatAdapter = MyListChatArchiveAdapter(this,chatMessages)
                            tableList.adapter = myListChatAdapter
                        }
                    }
                }
            }
        }

        ListaStolyButton.setOnClickListener {
            finish()
        }
    }
}