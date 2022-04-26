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

class MyListSpellAdapter(private val context: Activity, private val spelllist: MutableList<SpellOnList>)
    : ArrayAdapter<SpellOnList>(context, R.layout.custom_spell_list, spelllist) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_spell_list, null, true)

        val nameSpell = rowView.findViewById(R.id.spellName) as TextView
        val valueSpell = rowView.findViewById(R.id.spellValue) as TextView
        val descSpell = rowView.findViewById(R.id.spellDesc) as TextView
        val spellDelete = rowView.findViewById(R.id.spellDelete) as Button

        nameSpell.text = spelllist[position].spellName
        valueSpell.text = "Koszt zaklęcia: " + spelllist[position].spellValue.toString() + " PM"
        descSpell.text = spelllist[position].spellDesc

        spellDelete.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val firebaseUser = FirebaseAuth.getInstance().currentUser!!
            db.collection("equipment").document(spelllist[position].id).delete().addOnSuccessListener {
                Toast.makeText(context, "Poprawnie usunięto zaklęcie", Toast.LENGTH_SHORT).show()
                spellDelete.isEnabled = true
                context.finish()
            }.addOnFailureListener{
                Toast.makeText(context, "Wystąpił błąd przy usuwaniu zaklęcia", Toast.LENGTH_SHORT).show()
                spellDelete.isEnabled = true
            }
        }
        return rowView
    }



}