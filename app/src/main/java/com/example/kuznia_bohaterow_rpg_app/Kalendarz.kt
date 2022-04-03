package com.example.kuznia_bohaterow_rpg_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_kalendarz.*
import java.util.*
import kotlin.collections.ArrayList


class Kalendarz : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    val db = FirebaseFirestore.getInstance()
    val firebaseUser = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalendarz)

        FirebaseFirestore.getInstance().collection("meetings").whereEqualTo("id",firebaseUser.uid).get().addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    for (data in task.result) {
                        var dbYear = data.data.getValue("year") as String
                        var dbMonth = data.data.getValue("month") as String
                        var dbDay = data.data.getValue("day") as String
                        var dbHour = data.data.getValue("hour") as String
                        var dbMinute = data.data.getValue("minute") as String

                        savedYear = dbYear.toInt()
                        savedMonth = dbMonth.toInt()
                        savedDay = dbMonth.toInt()
                        savedHour = dbHour.toInt()
                        savedMinute = dbMinute.toInt()

                        Log.e("logData",dbYear)
                        Log.e("logData",savedYear.toString())
                        Log.e("logData",dbMonth)
                        Log.e("logData",dbDay)
                        Log.e("logData",dbHour)
                        Log.e("logData",dbMinute)

                        KTextPoka.text ="Następne spotkanie odbędzie sie: \n $savedDay-$savedMonth-$savedYear\n $savedHour:$savedMinute"

                    }
                }
            }

        pickDate()

        }




    private fun pickDate(){
        KButtonData.setOnClickListener {
                getDateTimeCalendar()

                val datePickerDialog = DatePickerDialog(this,this,year,month,day)
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show()

                val userMeetingInformation: MutableMap<String, String> = HashMap()

                userMeetingInformation["id"] = firebaseUser.uid
                userMeetingInformation["year"] = year.toString()
                userMeetingInformation["month"] = month.toString()
                userMeetingInformation["day"] = day.toString()
                userMeetingInformation["hour"] = hour.toString()
                userMeetingInformation["minute"] = minute.toString()

                Log.e("logData",year.toString())
                Log.e("logData",month.toString())
                Log.e("logData",day.toString())
                Log.e("logData",hour.toString())
                Log.e("logData",minute.toString())

                db.collection("meetings").document(firebaseUser.uid).delete().addOnCompleteListener {
                    db.collection("meetings").document(firebaseUser.uid).set(userMeetingInformation).addOnSuccessListener {
                        Toast.makeText(this, "Poprawnie dodano spotkanie", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "Wystąpił nieoczekiwany błąd: " + e, Toast.LENGTH_SHORT).show()
                    }
                }



        }
    }

    private fun getDateTimeCalendar(){
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)



    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedDay = p3
        savedMonth = p2
        savedYear = p1

        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute,true).show()


    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedHour = p1
        savedMinute = p2

        KTextPoka.text ="Następne spotkanie odbędzie sie: \n $savedDay-$savedMonth-$savedYear\n $savedHour:$savedMinute"

    }




}