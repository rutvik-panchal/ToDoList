package com.rutvik.apps.todolist.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.rutvik.apps.todolist.R
import com.rutvik.apps.todolist.onboarding.OnBoardingActivity
import com.rutvik.apps.todolist.utils.AppConstant
import com.rutvik.apps.todolist.utils.PrefConstant
import com.rutvik.apps.todolist.utils.SharedPreferencesHelper


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SharedPreferencesHelper.init(this)
        checkIfOnBoarded()
        getFCMToken()
    }

    private fun checkIfOnBoarded() {
        val hasOnBoarded : Boolean = SharedPreferencesHelper.readBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY)
        if (hasOnBoarded) {
            checkLoginStatus()
        } else {
            startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
            this.finish()
        }
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("SplashActivity", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    val token = task.result!!.token
                    Log.d("SplashActivity", token)
                    //Toast.makeText(this@SplashActivity, token, Toast.LENGTH_SHORT).show()
                })
    }

    private fun checkLoginStatus() {
        val isLoggedIn = SharedPreferencesHelper.readBoolean(PrefConstant.IS_LOGGED_IN)
        if (!isLoggedIn) {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MyNotesActivity::class.java).apply {
                putExtra(AppConstant.FULL_NAME, SharedPreferencesHelper.readString(PrefConstant.USER_FULL_NAME))
            })
        }
        finish()
    }
}