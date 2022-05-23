package com.example.kuznia_bohaterow_rpg_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_character_on_list.*
import kotlinx.android.synthetic.main.activity_ekran_postaci.*

class CheckCharacterOnList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_character_on_list)

        val idCharacter = intent.getStringExtra("id").toString()


        FirebaseFirestore.getInstance().collection("charactersheet").document(idCharacter).get().addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                CPTextImie.text = task.result["name"].toString();
                CPTextZawod.text = task.result["job"].toString();
                CPLiczbaSila.text = task.result["STR"].toString();
                CPLiczbaZrecznosc.text = task.result["DEX"].toString();
                CPLiczbaBudowaCiala.text = task.result["SIZ"].toString();
                CPLiczbaKondycja.text = task.result["CON"].toString();
                CPLiczbaWyglad.text = task.result["APP"].toString();
                CPLiczbaWyksztalcenie.text = task.result["EDU"].toString();
                CPLiczbaMoc.text = task.result["POW"].toString();
                CPLiczbaInteligencja.text = task.result["INT"].toString();
                CPLiczbaPoczytalnosc.text = task.result["sanity"].toString();
                CPLiczbaMoc.text = task.result["POW"].toString();

                Glide.with(this).load(task.result["imgURL"]).override(100,100).centerCrop().into(CPAvatarImage)
            }
        }

        CPButtonPowrot.setOnClickListener {
            finish()
        }
    }
}