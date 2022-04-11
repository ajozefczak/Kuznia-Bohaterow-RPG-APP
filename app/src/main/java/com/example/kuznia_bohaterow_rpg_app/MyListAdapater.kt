package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyListAdapter(private val context: Activity, private val title: MutableList<String>, private val description: MutableList<String>, private val imgid: MutableList<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.characterName) as TextView
        //tu jest przekazywany url w postaci Stringa
        val imageView = rowView.findViewById(R.id.characterAvatar) as ImageView
        val subtitleText = rowView.findViewById(R.id.characterOccupation) as TextView

        titleText.text = title[position]
        Glide.with(context).load(imgid[position]).override(100,100).centerCrop().into(imageView)
        subtitleText.text = description[position]

        return rowView
    }
}