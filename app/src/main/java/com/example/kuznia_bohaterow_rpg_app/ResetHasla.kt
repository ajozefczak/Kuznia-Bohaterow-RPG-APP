package com.example.kuznia_bohaterow_rpg_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_reset_hasla.*

class ResetHasla : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_hasla)

        RButtonWyslijResetHasla.setOnClickListener {
            RButtonWyslijResetHasla.isEnabled = false
            if(RHEditTextEmailWpisanie.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.WprowadzEmail, Toast.LENGTH_SHORT).show()
                RButtonWyslijResetHasla.isEnabled = true
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(RHEditTextEmailWpisanie.text.toString())
                        Toast.makeText(this, R.string.JezeliDane, Toast.LENGTH_SHORT).show()
                RButtonWyslijResetHasla.isEnabled = true
                        finish()
                }

        }


    }
}