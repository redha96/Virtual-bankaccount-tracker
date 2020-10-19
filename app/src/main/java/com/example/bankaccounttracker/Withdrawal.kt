package com.example.bankaccounttracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.bottomNavigationView
import kotlinx.android.synthetic.main.activity_withdrawal.*

class Withdrawal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawal)


        supportActionBar?.setTitle(Html.fromHtml("<font color  =\"#842c6d\"> " + getString(R.string.app_name) + "</font>"))


        bottomNavigationView.setSelectedItemId(R.id.withdrawal_nav_btn)



        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.profile_nav_btn -> {
                    val intent = Intent(this , Profile::class.java)
                    startActivity(intent)
                    true
                }

//                R.id.withdrawal_nav_btn -> {
//                    val intent = Intent(this , Withdrawal::class.java)
//                    startActivity(intent)
//                    true
//                }

                R.id.deposit_nav_btn -> {
                    val intent = Intent(this , Deposit::class.java)
                    startActivity(intent)
                    true
                }

                R.id.periodic_deposit_nav_btn -> {
                    val intent = Intent(this , PeriodicDeposit::class.java)
                    startActivity(intent)
                    true
                }


                else -> false
            }
        }

        bottomNavigationView.setSelectedItemId(R.id.withdrawal_nav_btn)
        val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        var userBalanceRef = db.collection("account tracker").document("users")
            .collection("$userID").document("balance info")
        userBalanceRef.get().addOnSuccessListener { documentSnapshot ->
            current_balance_value_text_view.setText(documentSnapshot.get("currentBalance").toString())
        }
            .addOnFailureListener{exception ->
                Log.d("withdrawal","error is $exception")
            }



        withdrawBtn.setOnClickListener{

            var withdrawAmountString =withdrawal_amount_edit_text.text.toString()
            if(withdrawAmountString.trim().length>0) {

                var withdrawAmount = withdrawAmountString.toDouble()
                withdrawAmount = -withdrawAmount

                userBalanceRef.update("currentBalance",FieldValue.increment(withdrawAmount)).addOnCompleteListener {
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