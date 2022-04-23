package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_przedmioty.*

class Przedmioty : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_przedmioty)
        initListeners()
        listItems()
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }


    private fun listItems() {

        val items: MutableList<ItemOnList> = mutableListOf()
        val idCharacter = intent.getStringExtra("id").toString()

        FirebaseFirestore.getInstance().collection("equipment").whereEqualTo("characterID", idCharacter).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (data in task.result) {
                    var tempName = data["itemName"].toString()
                    var tempValue = Integer.parseInt(data["itemValue"].toString())
                    var tempDesc = data["itemDescription"].toString()
                    var tempIds = data.id

                    var itemtemp = ItemOnList(tempName, tempValue, tempDesc, tempIds)
                    items.add(itemtemp)
                }

                items.sortBy {it.itemName}
                val MyListItemAdapter = MyListItemAdapter(this,items)
                przedmiotyList.adapter = MyListItemAdapter
            }
        }

    }
    private fun initListeners(){
        val ButtonDodajPrzedmiot = findViewById<Button>(R.id.PrzedmiotButtonDodajPrzedmiot)
        ButtonDodajPrzedmiot.setOnClickListener(ButtonDodajPrzedmiotListener)

        PrzedmiotButtonCofnij.setOnClickListener {
            finish()
        }
    }
    private val ButtonDodajPrzedmiotListener = View.OnClickListener { callButtonDodajPrzedmiotListenerActivity() }

    private fun callButtonDodajPrzedmiotListenerActivity() {
        val DodajPrzedmiotIntent = Intent(this, DodajPrzedmiot::class.java)
        val idCharacter = intent.getStringExtra("id").toString()
        DodajPrzedmiotIntent.putExtra("id",idCharacter)
        startActivity(DodajPrzedmiotIntent)
    }


}