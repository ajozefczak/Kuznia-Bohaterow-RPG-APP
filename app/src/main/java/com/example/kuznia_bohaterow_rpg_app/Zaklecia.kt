package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_przedmioty.*
import kotlinx.android.synthetic.main.activity_zaklecia.*

class Zaklecia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zaklecia)
        initListeners()
        listSpells()
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }


    private fun listSpells() {

        val spells: MutableList<SpellOnList> = mutableListOf()
        val idCharacter = intent.getStringExtra("id").toString()

        FirebaseFirestore.getInstance().collection("spells").whereEqualTo("characterID", idCharacter).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (data in task.result) {
                    var tempName = data["spellName"].toString()
                    var tempValue = Integer.parseInt(data["spellCost"].toString())
                    var tempDesc = data["spellDescription"].toString()
                    var tempIds = data.id

                    var spellTemp = SpellOnList(tempName, tempValue, tempDesc, tempIds)
                    spells.add(spellTemp)
                }

                spells.sortBy {it.spellName}
                val MyListSpellAdapter = MyListSpellAdapter(this,spells)
                zakleciaList.adapter = MyListSpellAdapter
            }
        }

    }

    private fun initListeners(){
        val ButtonDodajZaklecie = findViewById<Button>(R.id.ZakleciaButtonDodajZaklecie)
        ButtonDodajZaklecie.setOnClickListener(ButtonDodajZaklecieListener)

        ZakleciaButtonCofnij.setOnClickListener {
            finish()
        }
    }
    private val ButtonDodajZaklecieListener = View.OnClickListener { callButtonDodajZaklecieListenerActivity() }

    private fun callButtonDodajZaklecieListenerActivity() {
        val DodajZaklecieIntent = Intent(this, DodajZaklecie::class.java)
        val idCharacter = intent.getStringExtra("id").toString()
        DodajZaklecieIntent.putExtra("id",idCharacter)
        startActivity(DodajZaklecieIntent)
    }
}