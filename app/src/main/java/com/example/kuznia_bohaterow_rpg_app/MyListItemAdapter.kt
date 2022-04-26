package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyListItemAdapter(private val context: Activity, private val itemlist: MutableList<ItemOnList>)
    : ArrayAdapter<ItemOnList>(context, R.layout.custom_item_list, itemlist) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_item_list, null, true)

        val nameItem = rowView.findViewById(R.id.itemName) as TextView
        val valueItem = rowView.findViewById(R.id.itemVal) as TextView
        val descItem = rowView.findViewById(R.id.itemDesc) as TextView
        val itemDelete = rowView.findViewById(R.id.itemDelete) as Button

        nameItem.text = itemlist[position].itemName
        valueItem.text = "Wartość: " + itemlist[position].itemValue.toString() + " $"
        descItem.text = itemlist[position].itemDesc

        itemDelete.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val firebaseUser = FirebaseAuth.getInstance().currentUser!!
            db.collection("equipment").document(itemlist[position].id).delete().addOnSuccessListener {
                Toast.makeText(context, "Poprawnie usunięto przedmiot", Toast.LENGTH_SHORT).show()
                itemDelete.isEnabled = true
                context.finish()
            }.addOnFailureListener{
                Toast.makeText(context, "Wystąpił błąd przy usuwaniu przedmiotu", Toast.LENGTH_SHORT).show()
                itemDelete.isEnabled = true
            }
        }
        return rowView
    }



}