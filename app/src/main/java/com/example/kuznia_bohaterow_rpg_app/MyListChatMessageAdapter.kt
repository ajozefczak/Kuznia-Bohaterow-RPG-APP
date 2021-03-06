package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

public class MyListChatMessageAdapter(private val context: Activity, private val chatlist: MutableList<MessagesOnList>)
    : ArrayAdapter<MessagesOnList>(context, R.layout.custom_chat_message, chatlist) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_chat_message, null, true)

        val nick = rowView.findViewById(R.id.nick) as TextView
        val message = rowView.findViewById(R.id.message) as TextView

        nick.text = chatlist[position].nick
        message.text = chatlist[position].message

        var intColorR = chatlist[position].colorR.toInt()
        var intColorG = chatlist[position].colorG.toInt()
        var intColorB = chatlist[position].colorB.toInt()


        nick.setTextColor(Color.rgb(intColorR, intColorG, intColorB))


        return rowView
    }

}
