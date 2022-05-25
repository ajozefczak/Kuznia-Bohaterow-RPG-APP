package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.*

class MyListChatArchiveAdapter(private val context: Activity, private val chatlist: MutableList<MessagesOnList>)
    : ArrayAdapter<MessagesOnList>(context, R.layout.custom_archive_list, chatlist) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_archive_list, null, true)

        val nick = rowView.findViewById(R.id.nickArchive) as TextView
        val message = rowView.findViewById(R.id.messageArchive) as TextView
        val date = rowView.findViewById(R.id.dateArchive) as TextView

        val dateRaw = LocalDateTime.parse(chatlist[position].date)

        nick.text = chatlist[position].nick
        message.text = chatlist[position].message

        var monthtemp = ""
        if(dateRaw.monthValue < 10) monthtemp = "0" + dateRaw.monthValue.toString()
        else monthtemp = dateRaw.monthValue.toString()
        date.text = dateRaw.dayOfMonth.toString() + "-" + monthtemp + "-" + dateRaw.year.toString() + " " + dateRaw.hour.toString() + ":" + dateRaw.minute.toString()

        var intColorR = chatlist[position].colorR.toInt()
        var intColorG = chatlist[position].colorG.toInt()
        var intColorB = chatlist[position].colorB.toInt()


        nick.setTextColor(Color.rgb(intColorR, intColorG, intColorB))
        //date.setTextColor(Color.rgb(intColorR, intColorG, intColorB))

        return rowView
    }

}