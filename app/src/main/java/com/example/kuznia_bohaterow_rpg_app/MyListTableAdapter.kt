package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyListTableAdapter(private val context: Activity, private val tablelist: MutableList<TableOnList>)
    : ArrayAdapter<TableOnList>(context, R.layout.custom_spell_list, tablelist) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_table_list, null, true)

        val nameTable = rowView.findViewById(R.id.tableNameText) as TextView
        val descTable = rowView.findViewById(R.id.tableDescText) as TextView
        val tableDelete = rowView.findViewById(R.id.tableEditButton) as Button

        nameTable.text = tablelist[position].tableName
        descTable.text = tablelist[position].tableDescription

        tableDelete.setOnClickListener {

        }
        return rowView
    }



}