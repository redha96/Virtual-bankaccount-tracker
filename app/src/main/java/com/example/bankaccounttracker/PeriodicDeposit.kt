package com.example.bankaccounttracker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.activity_deposit.*
import kotlinx.android.synthetic.main.activity_periodic_deposit.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.bottomNavigationView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PeriodicDepositInfo(var depositAmount : Double , var period : String , var gainAmount : Double , var startDateOfDeposit : String , var endDateOfDeposit : String)


class PeriodicDeposit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_periodic_deposit)

        supportActionBar?.setTitle(Html.fromHtml("<font color  =\"#842c6d\"> " + getString(R.string.app_name) + "</font>"))


        bottomNavigationView.setSelectedItemId(R.id.periodic_deposit_nav_btn)



        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.profile_nav_btn -> {
                    val intent = Intent(this , Profile::class.java)
                    startActivity(intent)
                    true
                }

                R.id.withdrawal_nav_btn -> {
                    val intent = Intent(this , Withdrawal::class.java)
                    startActivity(intent)
                    true
                }

                R.id.deposit_nav_btn -> {
                    val intent = Intent(this , Deposit::class.java)
                    startActivity(intent)
                    true
                }

//                R.id.periodic_deposit_nav_btn -> {
//                    val intent = Intent(this , PeriodicDeposit::class.java)
//                    startActivity(intent)
//                    true
//                }


                else -> false
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun periodicDepositBtnOnClickListener(view : View){


        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val formatted = current.format(formatter)

        var dayOfDeposit = formatted[6].toString().plus(formatted[7].toString())
        var monthOfDeposit = formatted[4].toString().plus(formatted[5].toString())
        var yearOfDeposit = formatted[0].toString().plus(formatted[1].toString().plus(formatted[2].toString().plus(formatted[3].toString())))

        var dateOfDeposit = dayOfDeposit + "/" + monthOfDeposit + "/" + yearOfDeposit

        //println("Current Date is: $formatted")
        when(view.id){
            R.id.three_month_deposit_btn ->{
                var tempDepositValue = amount_of_three_month_deposit_edit_text.text.toString().toDouble()
                var tempDepositPeriod = "three month"

                var tempAmountThatGain = tempDepositValue * 1.03


                var endMonthOfDeposit = monthOfDeposit.toInt()
                var endYearOfDeposit = yearOfDeposit.toInt()
                endMonthOfDeposit += 3
                if(endMonthOfDeposit > 12){
                    endMonthOfDeposit -= 12
                    endYearOfDeposit += 1
                }

                var endDateOfDeposit = dayOfDeposit + "/" + endMonthOfDeposit.toString() + "/" + endYearOfDeposit.toString()

                var periodicDepositInfo = PeriodicDepositInfo(tempDepositValue,tempDepositPeriod,tempAmountThatGain,dateOfDeposit,endDateOfDeposit)
                setPeriodicDepositToDatabase(periodicDepositInfo)


            }


            R.id.six_month_deposit_btn ->{
                var tempDepositValue = amount_of_six_month_deposit_edit_text.text.toString().toDouble()
                var tempDepositPeriod = "six month"

                var tempAmountThatGain = tempDepositValue * 1.04

                var endMonthOfDeposit = monthOfDeposit.toInt()
                var endYearOfDeposit = yearOfDeposit.toInt()
                endMonthOfDeposit += 6
                if(endMonthOfDeposit > 12){
                    endMonthOfDeposit -= 12
                    endYearOfDeposit += 1
                }

                var endDateOfDeposit = dayOfDeposit + "/" + endMonthOfDeposit.toString() + "/" + endYearOfDeposit.toString()

                var periodicDepositInfo = PeriodicDepositInfo(tempDepositValue,tempDepositPeriod,tempAmountThatGain,dateOfDeposit,endDateOfDeposit)
                setPeriodicDepositToDatabase(periodicDepositInfo)



            }


            R.id.nine_month_deposit_btn ->{

                var tempDepositValue = amount_of_nine_month_deposit_edit_text.text.toString().toDouble()
                var tempDepositPeriod = "nine month"

                var tempAmountThatGain = tempDepositValue * 1.05


                var endMonthOfDeposit = monthOfDeposit.toInt()
                var endYearOfDeposit = yearOfDeposit.toInt()
                endMonthOfDeposit += 9
                if(endMonthOfDeposit > 12){
                    endMonthOfDeposit -= 12
                    endYearOfDeposit += 1
                }

                var endDateOfDeposit = dayOfDeposit + "/" + endMonthOfDeposit.toString() + "/" + endYearOfDeposit.toString()

                var periodicDepositInfo = PeriodicDepositInfo(tempDepositValue,tempDepositPeriod,tempAmountThatGain,dateOfDeposit,endDateOfDeposit)
                setPeriodicDepositToDatabase(periodicDepositInfo)



            }


            R.id.one_year_deposit_btn ->{

                var tempDepositValue = amount_of_one_year_deposit_edit_text.text.toString().toDouble()
                var tempDepositPeriod = "one year"

                var tempAmountThatGain = tempDepositValue * 1.065



                var endYearOfDeposit = yearOfDeposit.toInt() + 1


                var endDateOfDeposit = dayOfDeposit + "/" + monthOfDeposit + "/" + endYearOfDeposit.toString()

                var periodicDepositInfo = PeriodicDepositInfo(tempDepositValue,tempDepositPeriod,tempAmountThatGain,dateOfDeposit,endDateOfDeposit)
                setPeriodicDepositToDatabase(periodicDepositInfo)



            }


            R.id.two_year_deposit_btn ->{

                var tempDepositValue = amount_of_two_year_deposit_edit_text.text.toString().toDouble()
                var tempDepositPeriod = "two year"

                var tempAmountThatGain = tempDepositValue * 1.085


                var endYearOfDeposit = yearOfDeposit.toInt() + 2

                var endDateOfDeposit = dayOfDeposit + "/" + monthOfDeposit + "/" + endYearOfDeposit.toString()


                var periodicDepositInfo = PeriodicDepositInfo(tempDepositValue,tempDepositPeriod,tempAmountThatGain,dateOfDeposit,endDateOfDeposit)
                setPeriodicDepositToDatabase(periodicDepositInfo)

            }


        }
    }


    fun setPeriodicDepositToDatabase(periodicDepositInformation : PeriodicDepositInfo) {


        var userId = FirebaseAuth.getInstance().currentUser?.uid


            var depositAmount = periodicDepositInformation.depositAmount
            depositAmount = -depositAmount


            var userBalanceRef = db.collection("account tracker").document("users")
                .collection("$userId").document("balance info")
            userBalanceRef.get()

            userBalanceRef.update("currentBalance", FieldValue.increment(depositAmount)).addOnCompleteListener {
                db.collection("account tracker").document("users").collection("$userId")
                    .document("periodic Deposit").set(periodicDepositInformation)

                    .addOnSuccessListener { Log.d("TAG", "periodic Deposit added")
                        Toast.makeText(this, "periodic Deposit added", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Failed to add your deposit : ${it.message}", Toast.LENGTH_LONG).show()

                    }
            }

    }
}