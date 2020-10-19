package com.example.bankaccounttracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.activity_deposit.*
import kotlinx.android.synthetic.main.activity_deposit.current_balance_value_text_view
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.bottomNavigationView
import kotlinx.android.synthetic.main.activity_withdrawal.*

class Deposit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit)

        supportActionBar?.setTitle(Html.fromHtml("<font color  =\"#842c6d\"> " + getString(R.string.app_name) + "</font>"))


        bottomNavigationView.setSelectedItemId(R.id.deposit_nav_btn)



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

//                R.id.deposit_nav_btn -> {
//                    val intent = Intent(this , Deposit::class.java)
//                    startActivity(intent)
//                    true
//                }

                R.id.periodic_deposit_nav_btn -> {
                    val intent = Intent(this , PeriodicDeposit::class.java)
                    startActivity(intent)
                    true
                }


                else -> false
            }
        }

        val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        var userBalanceRef = db.collection("account tracker").document("users")
            .collection("$userID").document("balance info")
        userBalanceRef.get().addOnSuccessListener { documentSnapshot ->
            current_balance_value_text_view.setText(documentSnapshot.get("currentBalance").toString())
        }
            .addOnFailureListener{exception ->
                Log.d("withdrawal","error is $exception")
            }


        depositBtn.setOnClickListener{
            var depositAmountString =deposit_amount_edit_text.text.toString()
            if(depositAmountString.trim().length>0) {

                var depositAmount = depositAmountString.toDouble()
                depositAmount = depositAmount

                userBalanceRef.update("currentBalance", FieldValue.increment(depositAmount)).addOnCompleteListener {
                    userBalanceRef.get().addOnSuccessListener { documentSnapshot ->
                        current_balance_value_text_view.setText(documentSnapshot.get("currentBalance").toString())
                    }
                }
            }
            else{
                Toast.makeText(applicationContext, "enter the amount that you want to withdraw", Toast.LENGTH_LONG).show()
            }
        }
    }
}