package com.example.kuznia_bohaterow_rpg_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_rozwoj_postaci.*

class RozwojPostaci : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rozwoj_postaci)

        val idCharacter = intent.getStringExtra("id").toString()

        FirebaseFirestore.getInstance().collection("charactersheet").document(idCharacter).get().addOnCompleteListener{task ->
            if(task.isSuccessful) {
                    editTextTextPersonName4.setText(task.result["STR"].toString())
                    editTextTextPersonName7.setText(task.result["CON"].toString())
                    editTextTextPersonName8.setText(task.result["SIZ"].toString())
                    editTextTextPersonName9.setText(task.result["DEX"].toString())
                    editTextTextPersonName10.setText(task.result["INT"].toString())
                    editTextTextPersonName11.setText(task.result["POW"].toString())
                    editTextTextPersonName12.setText(task.result["EDU"].toString())
                    editTextTextPersonName13.setText(task.result["sanity"].toString())
                    editTextTextPersonName14.setText(task.result["APP"].toString())
            }
        }



        button2.setOnClickListener{
            var pepega1 = Integer.parseInt(editTextTextPersonName4.text.toString())
            var pepega2 = Integer.parseInt(editTextTextPersonName7.text.toString())
            var pepega3 = Integer.parseInt(editTextTextPersonName8.text.toString())
            var pepega4 = Integer.parseInt(editTextTextPersonName9.text.toString())
            var pepega5 = Integer.parseInt(editTextTextPersonName10.text.toString())
            var pepega6 = Integer.parseInt(editTextTextPersonName11.text.toString())
            var pepega7 = Integer.parseInt(editTextTextPersonName12.text.toString())
            var pepega8 = Integer.parseInt(editTextTextPersonName13.text.toString())
            var pepega9 = Integer.parseInt(editTextTextPersonName14.text.toString())
            if(
                !editTextTextPersonName4.text.isEmpty() &&
                pepega1 > 0 && pepega1 < 100 &&
                !editTextTextPersonName7.text.isEmpty() &&
                pepega2 > 0 && pepega2 < 100 &&
                !editTextTextPersonName8.text.isEmpty() &&
                pepega3 > 0 && pepega3 < 100 &&
                !editTextTextPersonName9.text.isEmpty() &&
                pepega4 > 0 && pepega4 < 100 &&
                !editTextTextPersonName10.text.isEmpty() &&
                pepega5 > 0 && pepega5 < 100 &&
                !editTextTextPersonName11.text.isEmpty() &&
                pepega6 > 0 && pepega6 < 100 &&
                !editTextTextPersonName12.text.isEmpty() &&
                pepega7 > 0 && pepega7 < 100 &&
                !editTextTextPersonName13.text.isEmpty() &&
                pepega8 > 0 && pepega8 < 100 &&
                !editTextTextPersonName14.text.isEmpty() &&
                pepega9 > 0 && pepega9 < 100
                    ) {
                button2.isEnabled = false
                FirebaseFirestore.getInstance().collection("charactersheet").document(idCharacter)
                    .update(
                        mapOf(
                            "STR" to editTextTextPersonName4.text.toString(),
                            "CON" to editTextTextPersonName7.text.toString(),
                            "SIZ" to editTextTextPersonName8.text.toString(),
                            "DEX" to editTextTextPersonName9.text.toString(),
                            "INT" to editTextTextPersonName10.text.toString(),
                            "POW" to editTextTextPersonName11.text.toString(),
                            "EDU" to editTextTextPersonName12.text.toString(),
                            "sanity" to editTextTextPersonName13.text.toString(),
                            "APP" to editTextTextPersonName14.text.toString()
                        )
                    )
                Toast.makeText(this, R.string.PoprawnieEdytowano, Toast.LENGTH_SHORT).show()
                button2.isEnabled = true
                finish()
            }else{
                Toast.makeText(this, R.string.BladPodczas, Toast.LENGTH_SHORT).show()
            }
        }

        RPCofnij.setOnClickListener {
            finish()
        }

        RozwojPostaciButtonD4.setOnClickListener {
            RozwojPostaciButtonD4.isEnabled = false
            val pepegaValue = (1..4).random()
            textView59.setText(pepegaValue.toString())
            RozwojPostaciButtonD4.isEnabled = true
        }

        RozwojPostaciButtonD6.setOnClickListener {
            RozwojPostaciButtonD6.isEnabled = false
            val pepegaValue = (1..6).random()
            textView59.setText(pepegaValue.toString())
            RozwojPostaciButtonD6.isEnabled = true
        }

        RozwojPostaciButtonD8.setOnClickListener {
            RozwojPostaciButtonD8.isEnabled = false
            val pepegaValue = (1..8).random()
            textView59.setText(pepegaValue.toString())
            RozwojPostaciButtonD8.isEnabled = true
        }

        RozwojPostaciButtonD10.setOnClickListener {
            RozwojPostaciButtonD10.isEnabled = false
            val pepegaValue = (1..10).random()
            textView59.setText(pepegaValue.toString())
            RozwojPostaciButtonD10.isEnabled = true
        }

        RozwojPostaciButtonD12.setOnClickListener {
            RozwojPostaciButtonD12.isEnabled = false
            val pepegaValue = (1..12).random()
            textView59.setText(pepegaValue.toString())
            RozwojPostaciButtonD12.isEnabled = true
        }

        RozwojPostaciButtonD20.setOnClickListener {
            RozwojPostaciButtonD20.isEnabled = false
            val pepegaValue = (1..20).random()
            textView59.setText(pepegaValue.toString())
            RozwojPostaciButtonD20.isEnabled = true
        }



    }


}