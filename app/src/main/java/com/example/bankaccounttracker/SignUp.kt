package com.example.bankaccounttracker

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.view.*
import java.util.*
import java.util.Calendar;
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.properties.Delegates


class PersonalInfo(var name : String ,    var phoneNumber : String     , var gender : String,
                   var address : String , var haveCreditCard : Boolean?, var birthday : String)


class CreditBalance( var currentBalance : Int)





class SignUp : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


//    lateinit var currentUser : PersonalInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

    supportActionBar?.setTitle(Html.fromHtml("<font color  =\"#842c6d\"> " + getString(R.string.app_name) + "</font>"))





        create_accountBtn.setOnClickListener{
            getNewAccount()
        }




        nextBtn.setOnClickListener{
            getNewAccount()

        }




//            birthday.setTransformationMethod
        birthday.setOnClickListener{

            var cldr = Calendar.getInstance()
            var day = cldr.get(Calendar.DAY_OF_MONTH)
            var month = cldr.get(Calendar.MONTH)
            var year = cldr.get(Calendar.YEAR)

            DatePickerDialog(this,this,year,month,day).show()


        }


    }


    fun getNewAccount (){
        val email = emailSignUp.text.toString()
        val password = passwordSignUpl.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(!it.isSuccessful)return@addOnCompleteListener

            //else if it successful
            var nameOfUser = name.text.toString()
            var phoneOfUser = phoneNumber.text.toString()
            var genderOfUser : String = "not defined"
            if(maleRadioBtn.isChecked)  genderOfUser = "male"; if(femaleRadioBtn.isChecked ) genderOfUser = "female"
            var addressOfUser = address.text.toString()
            var userHaveCreditCard by Delegates.notNull<Boolean>()
            if(yesHaveCreditRadioBtn.isChecked)  userHaveCreditCard = true; else if(notHaveCreditRadioBtn.isChecked )  userHaveCreditCard = false

            if(yesHaveCreditRadioBtn.isChecked) userHaveCreditCard = true
            if(notHaveCreditRadioBtn.isChecked) userHaveCreditCard = false
            var birthdayOfUser = birthday.text.toString()

            var currentUser = PersonalInfo(nameOfUser,phoneOfUser,genderOfUser,addressOfUser,userHaveCreditCard,birthdayOfUser)


            var uid = FirebaseAuth.getInstance().currentUser?.uid
            db.collection("account tracker").document("users").collection("$uid")
                .document("user info").set(currentUser)

                .addOnSuccessListener { Log.d("TAG" , "Account info is added") }
                .addOnCompleteListener{
                   // var currentBalance =0
                    var userCreditBalance = CreditBalance(0)
                    db.collection("account tracker").document("users").collection("$uid")
                        .document("balance info").set(userCreditBalance).addOnSuccessListener {
                            Log.d("TAG" , "balace info added ")
                        }
//
//                    db.collection("account tracker").document("users").collection("$uid")
//                        .document("balance info").s
//                    val docRef = db.collection("account tracker").document("users").collection("$uid")
//                        .document("balance info")
//
//// Update the timestamp field with the value from the server
//                    val updates = hashMapOf<String, Any>(
//                        "current balance" to currentBalance
//                    )
//
//                    docRef.update(updates).addOnCompleteListener{
                        .addOnCompleteListener{
                                if (yesHaveCreditRadioBtn.isChecked) {
                                    val intent = Intent(this, CreditCard::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                } else if (notHaveCreditRadioBtn.isChecked) {
                                    val intent = Intent(this, Profile::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                }
                        }
                }
                .addOnFailureListener { Log.d("TAG" , "ERROR ") }


        }
    }
    override fun onDateSet(view: DatePicker?, year1: Int, monthOfYear: Int, dayOfMonth: Int) {

        var dayOfBirth = dayOfMonth
        var monthOfBirth = monthOfYear+1
        var yearOfBirth = year1
        var birthdayText = "$dayOfBirth/$monthOfBirth/$yearOfBirth"
        birthday.setText(birthdayText)

    }




//we will need these lines when we should send the data to the data base


    fun onGenderRadioButtonClicked(view: View) {
//        if (view is RadioButton) {
//            // Is the button now checked?
//            val checked = view.isChecked
//
//            // Check which radio button was clicked
//            when (view.getId()) {
//                R.id.maleRadioBtn ->
//                    if (checked) {
//                        tempPerson.gender= "male"
//                    }
//
//                R.id.femaleRadioBtn ->
//                    if (checked) {
//                        tempPerson.gender= "female"
//                    }
//
//            }
//        }
    }




    fun onHaveCreditRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.yesHaveCreditRadioBtn ->
                    if (checked) {
                        nextBtn.isEnabled   = true
                        nextBtn.isClickable = true
                        create_accountBtn.isEnabled   = false
                        create_accountBtn.isClickable = false
                    }

                R.id.notHaveCreditRadioBtn ->
                    if (checked) {
                        create_accountBtn.isEnabled   = true
                        create_accountBtn.isClickable = true
                        nextBtn.isEnabled   = false
                        nextBtn.isClickable = false
                    }

            }
        }
    }








}