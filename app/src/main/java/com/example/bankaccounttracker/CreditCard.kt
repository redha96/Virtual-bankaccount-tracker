package com.example.bankaccounttracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_credit_card.*

class CreditCardInfo( var cardType : String , var userCreditCardNumber : String , var cardExpire : String , var cvv : Int)


class CreditCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)


        supportActionBar?.setTitle(Html.fromHtml("<font color  =\"#842c6d\"> " + getString(R.string.app_name) + "</font>"))


        val typesOfCreditCard = arrayOf("MasterCard" , "Visa" , "PayPal" , "WESTERN UNION", "SWITCH")
        val typesOfCardArray = ArrayAdapter(this, android.R.layout.simple_spinner_item , typesOfCreditCard)
        type_of_credit_card_spinner.adapter = typesOfCardArray           //attached inputArrayAdapter to inputSpinenr


        create_accountBtn.setOnClickListener{
            var userCardType = type_of_credit_card_spinner.selectedItem.toString()
            var userCardNumber = card_number_edit_text.text.toString()
            //for testing
            var userCreditNumber = card_number_edit_text.text.toString()

            var userCardExpire = card_expire_edit_text.text.toString()
            var userCvv = cvv_edit_text.text.toString().toInt()

            var userCreditCardInfo = CreditCardInfo(userCardType , userCreditNumber , userCardExpire , userCvv)

            var uid = FirebaseAuth.getInstance().currentUser?.uid
            db.collection("account tracker").document("users").collection("$uid")
                .document("credit card info").set(userCreditCardInfo)
                .addOnSuccessListener { Log.d("credit_card" , "Account info is added")
                    val intent = Intent(this , Profile::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener { Log.d("credit_card" , "ERROR ") }


        }






//        val spinner: Spinner = findViewById(R.id.type_of_credit_card_spinner)
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter.createFromResource(
//            this,
//            R.array.type_of_credit_card_array,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinner.adapter = adapter
//        }
    }
}