package com.example.quizy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizy.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
          lateinit var firebaseAuth: FirebaseAuth
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
            firebaseAuth= FirebaseAuth.getInstance()
            btLogin.setOnClickListener {
                login() }

            btSignup.setOnClickListener(){
                val intent= Intent(this, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }
    }

    private fun login(){
        val email=etEmailAddress.text.toString()
        val password=etPassword.text.toString()

        if(email.isBlank()||password.isBlank()){
            Toast.makeText(this,"Email or Password can't be Blank",Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"Login successfully",Toast.LENGTH_SHORT).show()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
