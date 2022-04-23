package com.example.kuznia_bohaterow_rpg_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_ekran_postaci.*

class EkranPostaci : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

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
        initListeners()
    }

    private fun initListeners(){
        val ButtonEkiwpunek = findViewById<Button>(R.id.EPButtonEkiwpunek)
        ButtonEkiwpunek.setOnClickListener(ButtonEkiwpunekListener)

        val ButtonHistoria = findViewById<Button>(R.id.EPButtonHistoria)
        ButtonHistoria.setOnClickListener(ButtonHistoriaListener)

        val ButtonZaklecia = findViewById<Button>(R.id.EPButtonZaklecia)
        ButtonZaklecia.setOnClickListener(ButtonZakleciaListener)

        val ButtonNotatki = findViewById<Button>(R.id.EPButtonNotatki)
        ButtonNotatki.setOnClickListener(ButtonNotatkiListener)

        val ButtonUsun = findViewById<Button>(R.id.EPButtonUsun)
        ButtonUsun.setOnClickListener(ButtonUsunListener)
    }


    private val ButtonEkiwpunekListener = View.OnClickListener { callButtonEkiwpunekListenerActivity() }
    private val ButtonHistoriaListener = View.OnClickListener { callButtonHistoriaListenerActivity() }
    private val ButtonZakleciaListener = View.OnClickListener { callButtonZakleciaActivity() }
    private val ButtonNotatkiListener = View.OnClickListener { callButtonNotatkiListenerActivity() }
    private val ButtonUsunListener = View.OnClickListener { callButtonUsun() }


    private fun callButtonEkiwpunekListenerActivity() {
        val EkiwpunekIntent = Intent(this, Przedmioty::class.java)
        val idCharacter = intent.getStringExtra("id").toString()
        EkiwpunekIntent.putExtra("id",idCharacter)
        startActivity(EkiwpunekIntent)
    }

    private fun callButtonZakleciaActivity() {
        val ZakleciaIntent = Intent(this, Zaklecia::class.java)
        val idCharacter = intent.getStringExtra("id").toString()
        ZakleciaIntent.putExtra("id",idCharacter)
        startActivity(ZakleciaIntent)
    }

    private fun callButtonHistoriaListenerActivity() {
        val HistoriaIntent = Intent(this, HistoriaPostaci::class.java)
        val idCharacter = intent.getStringExtra("id").toString()
        HistoriaIntent.putExtra("id",idCharacter)
        startActivity(HistoriaIntent)
    }

    private fun callButtonNotatkiListenerActivity() {
        val NotatkiIntent = Intent(this, NotatkiGracza::class.java)
        val idCharacter = intent.getStringExtra("id").toString()
        NotatkiIntent.putExtra("id",idCharacter)
        startActivity(NotatkiIntent)
    }

    private fun callButtonUsun() {

        EPButtonUsun.isEnabled = false

        val idCharacter = intent.getStringExtra("id").toString()

        db.collection("charactersheet").document(idCharacter).delete().addOnSuccessListener {
            Toast.makeText(this, "Poprawnie usunięto postać", Toast.LENGTH_SHORT).show()
            val listaPostaciIntent = Intent(this, ListaPostaci::class.java)
            startActivity(listaPostaciIntent)
            finish()
            EPButtonUsun.isEnabled = true
        }.addOnFailureListener{
            Toast.makeText(this, "Wystąpił błąd przy usuwaniu postaci", Toast.LENGTH_SHORT).show()
            EPButtonUsun.isEnabled = true
        }
    }

}