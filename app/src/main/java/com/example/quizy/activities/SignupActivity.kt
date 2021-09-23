package com.example.quizy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizy.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth=FirebaseAuth.getInstance()
        btnSignup.setOnClickListener { signupuser() }

        btLogin.setOnClickListener(){
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signupuser(){
        val email= etEmailAddress.text.toString()
        val password =etPassword.text.toString()
        val confirmPassword =etConfirmPassword.text.toString()

        if(email.isBlank()||password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this,"Email or Password can't be Blank",Toast.LENGTH_SHORT).show()
            return
        }
        if(password!=confirmPassword){
            Toast.makeText(this,"Password and ConfirmPassword Doesn't Matched",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Sign Up successfully",Toast.LENGTH_SHORT).show()
                    val intent= Intent(this,
                        MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Error in Creating User", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
