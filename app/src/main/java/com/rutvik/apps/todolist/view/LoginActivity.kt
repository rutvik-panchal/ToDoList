package com.rutvik.apps.todolist.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rutvik.apps.todolist.utils.AppConstant
import com.rutvik.apps.todolist.utils.PrefConstant
import com.rutvik.apps.todolist.R
import com.rutvik.apps.todolist.utils.SharedPreferencesHelper

class LoginActivity : AppCompatActivity() {

    lateinit var editTextFullName: EditText
    lateinit var editTextUserName: EditText
    lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindView()
        SharedPreferencesHelper.init(this)
        buttonLogin.setOnClickListener {
            val fullName = editTextFullName.text.toString()
            val userName = editTextFullName.text.toString()
            if (fullName.isNotEmpty() && userName.isNotEmpty()) {
                val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                intent.putExtra(AppConstant.FULL_NAME, fullName)
                startActivity(intent)
                saveLoginStatusAsTrue()
                saveFullName(fullName)
                finish()
            } else {
                Toast.makeText(this@LoginActivity,
                        "Full Name and User Name can't be empty!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun bindView() {
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
    }

    private fun saveLoginStatusAsTrue() {
        SharedPreferencesHelper.write(PrefConstant.IS_LOGGED_IN, true)
    }

    private fun saveFullName(name: String) {
        SharedPreferencesHelper.write(PrefConstant.USER_FULL_NAME, name)
    }
}