package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lista_postaci.*
import kotlinx.android.synthetic.main.activity_lista_stoly.*

class ListaStoly : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_stoly)

        val tables: MutableList<TableOnList> = mutableListOf()

        FirebaseFirestore.getInstance().collection("tables").whereEqualTo("gmID", firebaseUser.uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (data in task.result) {
                    var tempTableName = data["tableName"].toString()
                    var tempTableDesc = data["tableDesc"].toString()
                    var tempJoinCode = data["joinCode"].toString()
                    var tempIds = data.id

                    var tableTemp = TableOnList(tempTableName, tempTableDesc, tempJoinCode, tempIds)
                    tables.add(tableTemp)
                }

                tables.sortBy { it.tableName }
                val myListTableAdapter = MyListTableAdapter(this, tables)
                tableList.adapter = myListTableAdapter

                tableList.setOnItemClickListener() { adapterView, view, position, id ->
                    val intent = Intent(this, Stoly::class.java)
                    intent.putExtra("id", tables[position].id)
                    startActivity(intent)
                    finish()
                }

                ListaStolyButton.setOnClickListener {
                    finish()
                }
            }
        }
    }
}