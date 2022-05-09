package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyListPlayerAdapter(private val context: Activity, private val playerlist: MutableList<PlayerOnList>)
    : ArrayAdapter<PlayerOnList>(context, R.layout.custom_players_list, playerlist) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_players_list, null, true)

        val playerCharacterName = rowView.findViewById(R.id.playerCharName) as TextView
        val playerName = rowView.findViewById(R.id.playerNick) as TextView

        playerCharacterName.text = playerlist[position].charname
        playerName.text = playerlist[position].nick


        return rowView
    }



}