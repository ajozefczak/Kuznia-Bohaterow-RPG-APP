package com.example.kuznia_bohaterow_rpg_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_kalendarz.*
import java.util.*


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

    val calendar = Calendar.getInstance()
    var currHour = calendar[Calendar.HOUR_OF_DAY]
    var currMinute = calendar[Calendar.MINUTE]
    var currDay = calendar[Calendar.DAY_OF_MONTH]
    var currMonth = calendar[Calendar.MONTH] + 1
    var currYear = calendar[Calendar.YEAR]

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
                        savedDay = dbDay.toInt()
                        savedHour = dbHour.toInt()
                        savedMinute = dbMinute.toInt()

                        Log.e("logData",dbYear)
                        Log.e("logData",savedYear.toString())
                        Log.e("logData",dbMonth)
                        Log.e("logData",dbDay)
                        Log.e("logData",dbHour)
                        Log.e("logData",dbMinute)

                        var tempMinute = ""
                        if(savedMinute < 10) {
                            tempMinute = "0" + savedMinute.toString()
                        }
                        else{
                            tempMinute = savedMinute.toString()
                        }

                        KTextPoka.text =R.string.NastepneSpotkanie.toString() + "\n" + "$savedDay-$savedMonth-$savedYear\n $savedHour:" + tempMinute

                    }
                }
            }

        pickDate()
        setDate()

            KButtonCofnij.setOnClickListener {
                KButtonCofnij.isEnabled = false
                /*val EkranGraczaIntent = Intent(this, EkranGracza::class.java)
                startActivity(EkranGraczaIntent)*/
                KButtonCofnij.isEnabled = true
                finish()
            }
        }




    private fun pickDate(){
        KButtonData.setOnClickListener {
            KButtonData.isEnabled = false
                getDateTimeCalendar()

                val datePickerDialog = DatePickerDialog(this,this,year,month,day)
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show()
            KButtonData.isEnabled = true
        }
    }


    private fun setDate(){
        KButtonZapiszTeermin.setOnClickListener() {
            KButtonZapiszTeermin.isEnabled = false
            val userMeetingInformation: MutableMap<String, String> = HashMap()

            userMeetingInformation["id"] = firebaseUser.uid
            userMeetingInformation["year"] = savedYear.toString()
            userMeetingInformation["month"] = savedMonth.toString()
            userMeetingInformation["day"] = savedDay.toString()
            userMeetingInformation["hour"] = savedHour.toString()
            userMeetingInformation["minute"] = savedMinute.toString()

            Log.e("logDataSetDate",savedDay.toString())
            Log.e("logDataSetDate",day.toString())
            Log.e("logDataSetDate","^^^day^^^")


            Log.e("logDataSetDate",savedMonth.toString())
            Log.e("logDataSetDate",month.toString())
            Log.e("logDataSetDate","^^^month^^^")


            Log.e("logDataSetDate",savedYear.toString())
            Log.e("logDataSetDate",year.toString())
            Log.e("logDataSetDate","^^^year^^^")


            Log.e("logDataSetDate",savedHour.toString())
            Log.e("logDataSetDate",hour.toString())
            Log.e("logDataSetDate","^^^hour^^^")


            Log.e("logDataSetDate",savedMinute.toString())
            Log.e("logDataSetDate",minute.toString())
            Log.e("logDataSetDate","^^^minute^^^")



            db.collection("meetings").document(firebaseUser.uid).delete().addOnCompleteListener {
                db.collection("meetings").document(firebaseUser.uid).set(userMeetingInformation).addOnSuccessListener {
                    Toast.makeText(this, R.string.PoprawnieDodanoSpotkanie, Toast.LENGTH_SHORT).show()
                    KButtonZapiszTeermin.isEnabled = true
                }.addOnFailureListener { e ->
                    Toast.makeText(this, R.string.WystapilBlad.toString() + e, Toast.LENGTH_SHORT).show()
                    KButtonZapiszTeermin.isEnabled = true
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
        savedMonth = p2 + 1
        savedYear = p1

        Log.e("logDataSetDate","------------" + savedMonth.toString())



        val timePickerDialog = TimePickerDialog(this,this,currHour,currMinute, true)
        timePickerDialog.show()


        getDateTimeCalendar()
        //TimePickerDialog(this,this,hour,minute,true).show()


    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

        Log.e("pepegaCal","Day " + savedDay.toString())
        Log.e("pepegaCal","Curr Day " + currDay.toString())
        Log.e("pepegaCal","Month " + savedMonth.toString())
        Log.e("pepegaCal","Curr Month " + currMonth.toString())
        Log.e("pepegaCal","Year " + savedYear.toString())
        Log.e("pepegaCal","Curr Year " + currYear.toString())

         currHour = calendar[Calendar.HOUR_OF_DAY]
         currMinute = calendar[Calendar.MINUTE]
         currDay = calendar[Calendar.DAY_OF_MONTH]
         currMonth = calendar[Calendar.MONTH] + 1
         currYear = calendar[Calendar.YEAR]

        if(savedDay==currDay && savedMonth==currMonth && savedYear==currYear) {
            if (p1 >= currHour && p2 >= currMinute) {
                savedHour = p1
                savedMinute = p2

                var tempMinute = ""
                if(savedMinute < 10) {
                    tempMinute = "0" + savedMinute.toString()
                }
                else{
                    tempMinute = savedMinute.toString()
                }

                KTextPoka.text =
                    getString(R.string.NastepneSpotkanie) + "\n" + "$savedDay-$savedMonth-$savedYear\n $savedHour:" + tempMinute
            } else {
                Toast.makeText(this, R.string.BlednaGodzina, Toast.LENGTH_LONG).show()
                val timePickerDialog = TimePickerDialog(this, this, currHour, currMinute, true)
                timePickerDialog.show()
            }
        }else{
            savedHour = p1
            savedMinute = p2

            var tempMinute = ""
            if(savedMinute < 10) {
                tempMinute = "0" + savedMinute.toString()
            }
            else{
                tempMinute = savedMinute.toString()
            }


            KTextPoka.text =
                getString(R.string.NastepneSpotkanie) + "\n" + "$savedDay-$savedMonth-$savedYear\n $savedHour:"+ tempMinute
        }
    }




}