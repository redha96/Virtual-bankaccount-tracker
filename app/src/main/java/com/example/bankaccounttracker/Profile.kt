package com.example.bankaccounttracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlin.math.log

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setTitle(Html.fromHtml("<font color  =\"#842c6d\"> " + getString(R.string.app_name) + "</font>"))


        bottomNavigationView.setSelectedItemId(R.id.profile_nav_btn)


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
//                R.id.profile_nav_btn -> {
//                    val intent = Intent(this , Profile::class.java)
//                    startActivity(intent)
//                    true
//                }

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

                R.id.periodic_deposit_nav_btn -> {
                    val intent = Intent(this , PeriodicDeposit::class.java)
                    startActivity(intent)
                    true
                }


                else -> false
            }
        }





        var userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        var userInfoRef = db.collection("account tracker").document("users")
            .collection("$userID").document("user info")
        userInfoRef.get().addOnSuccessListener { documentSnapshot ->
            if(documentSnapshot!=null) {
                nameOfProfile.setText("name : " + documentSnapshot.getString("name"))
                emailOfProfile.setText("email : " + FirebaseAuth.getInstance().currentUser?.email.toString())
                genderOfProfile.setText("gender : " + documentSnapshot.getString("gender"))
                birthdayOfProfile.setText(documentSnapshot.getString("birthday"))
                addressOfProfile.setText(documentSnapshot.getString("address"))
                have_credit_card_text_view.setText(documentSnapshot.get("haveCreditCard").toString())
            }
        }
        
            .addOnFailureListener{exception ->  
                Log.d("error","error is $exception")
            }

        var userPeriodicDepositRef = db.collection("account tracker").document("users").collection("$userID")
            ?.document("periodic Deposit")


        userPeriodicDepositRef.get().addOnSuccessListener { documentSnapshot ->


            if (documentSnapshot.getString("period").toString() != "null") {


//                if (have_credit_card_text_view.text.toString() == "true") {
//
////                    periodic_deposit_layout_profile.visibility = View.VISIBLE
//
//
//
//                    val periodicDepositConstraintLayout = periodic_deposit_layout_profile
//                    periodicDepositConstraintLayout.removeView(periodic_deposit_layout_profile)
//                    periodicDepositConstraintLayout.id = periodic_deposit_layout_profile.id
//
//                    profile_info_layout.addView(periodicDepositConstraintLayout)
//                    setContentView(profile_info_layout)
//
//                    val set = ConstraintSet()
//
//                    set.connect(
//                        periodicDepositConstraintLayout.id, ConstraintSet.LEFT,
//                        ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 20
//                    )
//
//                    set.connect(
//                        periodicDepositConstraintLayout.id, ConstraintSet.RIGHT,
//                        ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 20
//                    )
//
//                    set.connect(
//                        periodicDepositConstraintLayout.id, ConstraintSet.TOP,
//                        credit_card_layout_profile.id, ConstraintSet.TOP, 0
//                    )
//
//                    set.connect(
//                        periodicDepositConstraintLayout.id, ConstraintSet.BOTTOM,
//                        ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0
//                    )
//
//                    credit_card_layout_profile.visibility = View.VISIBLE
//                    set.applyTo(profile_info_layout)
//
//
////                        val set = ConstraintSet()
////                        set.clone(profile_info_layout)
////                        set.connect(periodic_deposit_layout_profile.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
////                        set.applyTo(constraintLayout)
//
//                }
                deposit_type_text_view.setText(
                    documentSnapshot.getString("period").toString() + " deposit"
                )

                start_and_end_of_deposit_text_view.setText(
                    "from " + documentSnapshot.getString("startDateOfDeposit") +
                            " to " + documentSnapshot.getString("endDateOfDeposit")
                )

                amount_you_put_and_gain_text_view.setText(
                    "you put : " + documentSnapshot.get("depositAmount").toString()
                            + "\n amount you will gain : " + documentSnapshot.get("gainAmount")
                )

                periodic_deposit_layout_profile.visibility = View.VISIBLE


                ////////////////////////////////////////////////////////

            }
        }









    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.nav_bar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this , LoginActivity::class.java)
                intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }

}