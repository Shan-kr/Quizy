package com.example.quizy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizy.R
import com.example.quizy.adapters.QuizAdapter
import com.example.quizy.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    lateinit var firestore: FirebaseFirestore
    private var quizList = mutableListOf<Quiz>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    fun setupViews(){
       setupFirestore()
        setupdrawerLayout()
        setupRecylerview()
        setUpDatePicker()
    }
    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
              //  val date = dateFormatter.format(Date(it))
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", dateFormatter)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)

            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }
    }
    fun setupFirestore(){
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if(value==null || error!=null){
                Toast.makeText(this,"Error Fetching Data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }
    fun setupRecylerview(){
        adapter = QuizAdapter(this,quizList)
        quizrecyclerView.layoutManager = GridLayoutManager(this,2)
        quizrecyclerView.adapter=adapter
    }
    fun setupdrawerLayout(){
        setSupportActionBar(AppBar)
        actionBarDrawerToggle= ActionBarDrawerToggle(this,mainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.btnProfile ->{
                    val intent =Intent(this,ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.logout->{
                    FirebaseAuth.getInstance().signOut()
                    val intent =Intent(this,LoginIntro::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.btnRateUs->{
                    Toast.makeText(this,"Rate us will be added",Toast.LENGTH_SHORT).show()
                }
                R.id.btnFollowUs->{
                    Toast.makeText(this,"Follow us will be added",Toast.LENGTH_SHORT).show()
                }
            }
            mainDrawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
