package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyListAdapter(private val context: Activity, private val charlist: MutableList<CharacterOnList>)
    : ArrayAdapter<CharacterOnList>(context, R.layout.custom_list, charlist) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.characterName) as TextView
        //tu jest przekazywany url w postaci Stringa
        val imageView = rowView.findViewById(R.id.characterAvatar) as ImageView
        val subtitleText = rowView.findViewById(R.id.characterOccupation) as TextView

        titleText.text = charlist[position].firstName
        Glide.with(context).load(charlist[position].avstr).override(100,100).centerCrop().into(imageView)
        subtitleText.text = charlist[position].occupa
        return rowView
    }
}