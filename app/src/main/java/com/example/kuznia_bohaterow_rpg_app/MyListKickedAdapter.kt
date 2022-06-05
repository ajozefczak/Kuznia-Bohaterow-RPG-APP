package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class MyListKickedAdapter (private val context: Activity, private val playerKickList: MutableList<PlayerKickOnList>)
    : ArrayAdapter<PlayerKickOnList>(context, R.layout.custom_player_kicked_list, playerKickList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_player_kicked_list, null, true)
        val itemDelete = rowView.findViewById(R.id.UnbanPlayer) as Button

        val nicknameText = rowView.findViewById(R.id.playerNicknameKicked) as TextView
        nicknameText.text = playerKickList[position].nick

        itemDelete.setOnClickListener {
            itemDelete.isEnabled = false
            val db = FirebaseFirestore.getInstance()
            FirebaseFirestore.getInstance().collection("blocklist").whereEqualTo("playerID", playerKickList[position].pID).whereEqualTo("tableID", playerKickList[position].tableID).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (data in task.result) {
                        FirebaseFirestore.getInstance().collection("blocklist").document(data.id).delete().addOnCompleteListener {
                            Toast.makeText(context, R.string.PoprawnieOdbanowano, Toast.LENGTH_SHORT).show()
                            context.finish()
                        }
                    }
                }
            }
        }

        return rowView
    }

}