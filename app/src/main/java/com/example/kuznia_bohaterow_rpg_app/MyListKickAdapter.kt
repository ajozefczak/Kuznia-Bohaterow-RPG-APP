package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyListKickAdapter(private val context: Activity, private val playerKickList: MutableList<PlayerKickOnList>)
    : ArrayAdapter<PlayerKickOnList>(context, R.layout.custom_player_kick_list, playerKickList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_player_kick_list, null, true)
        val itemDelete = rowView.findViewById(R.id.KickButton) as Button

        val nicknameText = rowView.findViewById(R.id.playerNicknameKick) as TextView
        nicknameText.text = playerKickList[position].nick

        itemDelete.setOnClickListener {
            itemDelete.isEnabled = false
            val db = FirebaseFirestore.getInstance()
            db.collection("tables_joins").document(playerKickList[position].joinID).delete().addOnCompleteListener() {
                Toast.makeText(context, "Poprawnie wyrzucono gracza ze stołu", Toast.LENGTH_SHORT).show()
                itemDelete.isEnabled = true
                context.finish()
            }.addOnFailureListener{
                Toast.makeText(context, "Wystąpił błąd przy wyrzucaniu gracza!", Toast.LENGTH_SHORT).show()
                itemDelete.isEnabled = true
            }
        }


        return rowView
    }

}