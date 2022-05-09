package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        ListaStolyButton.setOnClickListener {
            finish()
        }

        FirebaseFirestore.getInstance().collection("tables").whereEqualTo("gmID", firebaseUser.uid).get().addOnCompleteListener { task ->
            if (task.isComplete) {
                for (data in task.result) {
                    var tempTableName = data["tableName"].toString()
                    var tempTableDesc = data["tableDesc"].toString()
                    var tempJoinCode = data["joinCode"].toString()
                    var tempGMId = data["gmID"].toString()
                    var tempIds = data.id

                    var tableTemp = TableOnList(tempTableName, tempTableDesc, tempJoinCode, tempGMId, tempIds)
                    tables.add(tableTemp)
                }
            }

            FirebaseFirestore.getInstance().collection("tables_joins").whereEqualTo("playerID", firebaseUser.uid).get().addOnCompleteListener { task ->
                if (task.isComplete) {
                    for (data in task.result) {
                        var tempTableID = data["tableID"].toString()

                        FirebaseFirestore.getInstance().collection("tables").document(tempTableID).get().addOnCompleteListener{ task2 ->
                            if(task2.isComplete) {
                                var tempTableName2 = task2.result["tableName"].toString()
                                var tempTableDesc2 = task2.result["tableDesc"].toString()
                                var tempJoinCode2 = task2.result["joinCode"].toString()
                                var tempGMId = task2.result["gmID"].toString()
                                var tempIds2 = task2.result.id

                                var tableTemp2 = TableOnList(tempTableName2, tempTableDesc2, tempJoinCode2, tempGMId, tempIds2)
                                println(tableTemp2.id)
                                tables.add(tableTemp2)
                            }

                        }
                    }
                }

                tables.sortBy { it.tableName }
                val myListTableAdapter = MyListTableAdapter(this, tables)
                tableList.adapter = myListTableAdapter

                tableList.setOnItemClickListener() { adapterView, view, position, id ->
                    val intent = Intent(this, Stoly::class.java)
                    intent.putExtra("tableID", tables[position].id)
                    intent.putExtra("gmID", tables[position].gmID)
                    intent.putExtra("joinCode", tables[position].tableCode)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}