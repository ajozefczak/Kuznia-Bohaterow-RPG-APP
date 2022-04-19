package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lista_postaci.*

class ListaPostaci : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_postaci)

        val characters: MutableList<CharacterOnList> = mutableListOf()
        //val characterNamesMutable: MutableList<String> = mutableListOf()
        //val characterOcupationsMutable: MutableList<String> = mutableListOf()
        //val characterAvatarsMutable: MutableList<String> = mutableListOf()
        //val characterIDsMutable: MutableList<String> = mutableListOf()

        FirebaseFirestore.getInstance().collection("charactersheet").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (data in task.result) {
                    var tempCharName = data["name"].toString()
                    var tempOccup = data["job"].toString()
                    var tempAvatars = data["imgURL"].toString()
                    var tempIds = data.id

                    var chartemp = CharacterOnList(tempCharName, tempOccup, tempAvatars, tempIds)
                    characters.add(chartemp)

                    //characterNamesMutable.add(data["name"].toString())
                    //characterOcupationsMutable.add(data["job"].toString())
                    //characterAvatarsMutable.add(data["imgURL"].toString())
                    //characterIDsMutable.add(data.id)
                    }

                characters.sortBy {it.firstName}
                val myListAdapter = MyListAdapter(this,characters)
                characterList.adapter = myListAdapter

                characterList.setOnItemClickListener(){adapterView, view, position, id ->
                    val intent = Intent(this,EkranPostaci::class.java)
                    intent.putExtra("id",characters[position].id)
                    startActivity(intent)
                    finish()
                }

                LiButtonCofnij.setOnClickListener {
                    LiButtonCofnij.isEnabled = false
                    /*val EkranGraczaIntent = Intent(this, EkranGracza::class.java)
                    startActivity(EkranGraczaIntent)*/
                    LiButtonCofnij.isEnabled = true
                    finish()
                }


            }
        }

    }
}