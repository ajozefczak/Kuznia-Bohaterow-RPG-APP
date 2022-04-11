package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lista_postaci.*
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter
import android.widget.ListView;
import android.widget.Toast;
import kotlinx.android.synthetic.main.activity_kalendarz.*
import kotlinx.android.synthetic.main.custom_list.*

class ListaPostaci : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_postaci)

        val characterNamesMutable: MutableList<String> = mutableListOf()
        val characterOcupationsMutable: MutableList<String> = mutableListOf()
        val characterAvatarsMutable: MutableList<String> = mutableListOf()
        val characterIDsMutable: MutableList<String> = mutableListOf()

        FirebaseFirestore.getInstance().collection("charactersheet").whereEqualTo("id", firebaseUser.uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (data in task.result) {
                    characterNamesMutable.add(data["name"].toString())
                    characterOcupationsMutable.add(data["job"].toString())
                    characterAvatarsMutable.add(data["imgURL"].toString())
                    characterIDsMutable.add(data.id)
                    }

                val myListAdapter = MyListAdapter(this,characterNamesMutable,characterOcupationsMutable,characterAvatarsMutable)
                characterList.adapter = myListAdapter

                characterList.setOnItemClickListener(){adapterView, view, position, id ->
                    val intent = Intent(this,EkranPostaci::class.java)
                    intent.putExtra("id",characterIDsMutable[position])
                    startActivity(intent)
                }

                LiButtonCofnij.setOnClickListener {
                    val EkranGraczaIntent = Intent(this, EkranGracza::class.java)
                    startActivity(EkranGraczaIntent)
                    finish()
                }


            }
        }

    }
}