package com.example.kuznia_bohaterow_rpg_app

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_rejestracja.*
import kotlinx.android.synthetic.main.activity_tworzenie_postaci.*
import java.util.*
import kotlin.collections.HashMap

class TworzeniePostaci : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tworzenie_postaci)

        imageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

        TPButtonZapiszPostac.setOnClickListener {
            if (selectedImg != null) {

                val firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val db = FirebaseFirestore.getInstance()
                val charactersheet: MutableMap<String, String> = HashMap()

                charactersheet["id"] = firebaseUser.uid
                charactersheet["name"] = editTextTextPersonName2.text.toString()
                charactersheet["job"] = editTextTextPersonName3.text.toString()
                charactersheet["sanity"] = PoczytalnoscLiczba.text.toString()
                charactersheet["EDU"] = WyksztalcenieLiczba.text.toString()
                charactersheet["POW"] = MocLiczba.text.toString()
                charactersheet["INT"] = InteligencjaLiczba.text.toString()
                charactersheet["DEX"] = ZrecznoscLiczba.text.toString()
                charactersheet["SIZ"] = BudowaCialaLiczba.text.toString()
                charactersheet["CON"] = KondycjaLiczba.text.toString()
                charactersheet["STR"] = SilaLiczba.text.toString()

                val imgFileName = UUID.randomUUID().toString()
                val imgToStore = FirebaseStorage.getInstance().getReference("/images/$imgFileName")
                imgToStore.putFile(selectedImg!!).addOnSuccessListener {
                    imgToStore.downloadUrl.addOnSuccessListener {
                        charactersheet["imgURL"] = it.toString()
                        db.collection("charactersheet").add(charactersheet)
                        Toast.makeText(this, "Pomyślnie stworzon postać", Toast.LENGTH_SHORT).show()
                        val EkranGraczaIntent = Intent(this, EkranGracza::class.java)
                        startActivity(EkranGraczaIntent)
                    }.addOnFailureListener {
                            e ->
                        Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                    }
                }


            }else{
                Toast.makeText(this, "Brak dodanego zdjęcia postaci", Toast.LENGTH_SHORT).show()
            }
        }

    }

    var selectedImg: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selectedImg = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedImg)

            val bitmapDrawable = BitmapDrawable(bitmap)
            imageButton.setBackgroundDrawable(bitmapDrawable)
            imageButton.setImageDrawable(null)
        }
    }

}