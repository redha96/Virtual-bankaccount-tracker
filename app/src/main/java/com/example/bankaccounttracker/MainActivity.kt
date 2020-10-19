package com.example.bankaccounttracker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

val db = Firebase.firestore

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(Html.fromHtml("<font color  =\"#842c6d\"> " + getString(R.string.app_name) + "</font>"))
                //"<font color=\"purple">" + getString(R.string.app_name) +  "));


        verifyUserIsLoggedIn()





//            val dialog = MaterialDialog(this).customView(R.layout.about_layout)
//
//            dialog.show()


        }


    private fun verifyUserIsLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null) {
            val intent = Intent(this , LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else{
            val docRef = db.collection("account tracker")
                .document("user").collection("$uid").document("user info")
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        if(document.getBoolean("haveCreditCard") == true){
                            val intent = Intent(this , CreditCard::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        else{
                            val intent = Intent(this , Profile::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        //Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }.addOnCompleteListener {

                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }




        }

    }
}

