package com.example.kuznia_bohaterow_rpg_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_ekran_postaci.*

class EkranPostaci : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ekran_postaci)

        val idCharacter = intent.getStringExtra("id").toString()

        FirebaseFirestore.getInstance().collection("charactersheet").document(idCharacter).get().addOnCompleteListener{ task ->
            if(task.isSuccessful) {
               EPTextImie.text = task.result["name"].toString();
               EPTextZawod.text = task.result["job"].toString();
               EPLiczbaSila.text = task.result["STR"].toString();
               EPLiczbaZrecznosc.text = task.result["DEX"].toString();
               EPLiczbaBudowaCiala.text = task.result["SIZ"].toString();
               EPLiczbaKondycja.text = task.result["CON"].toString();
               EPLiczbaWyglad.text = task.result["APP"].toString();
               EPLiczbaWyksztalcenie.text = task.result["EDU"].toString();
               EPLiczbaMoc.text = task.result["POW"].toString();
               EPLiczbaInteligencja.text = task.result["INT"].toString();
               EPLiczbaPoczytalnosc.text = task.result["sanity"].toString();
               EPLiczbaMoc.text = task.result["POW"].toString();

               Glide.with(this).load(task.result["imgURL"]).override(100,100).centerCrop().into(EPAvatarImage)
            }
        }


    }
}