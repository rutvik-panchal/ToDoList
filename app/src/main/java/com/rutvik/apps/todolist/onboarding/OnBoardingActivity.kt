package com.rutvik.apps.todolist.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.rutvik.apps.todolist.R
import com.rutvik.apps.todolist.utils.PrefConstant
import com.rutvik.apps.todolist.utils.SharedPreferencesHelper
import com.rutvik.apps.todolist.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnClickOnBoardingOne, OnBoardingTwoFragment.OnClickOnBoardingTwo {
    
    lateinit var viewPagerOnBoarding : ViewPager

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindViews()
        SharedPreferencesHelper.init(this)
        configViewPager()

    }

    private fun configViewPager() {
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPagerOnBoarding.adapter = adapter
    }

    private fun bindViews() {
        viewPagerOnBoarding = findViewById(R.id.viewPageOnBoarding)
    }

    override fun onNextClickOne() {
        viewPagerOnBoarding.currentItem = 1
    }

    override fun onDoneClickTwo() {
        saveStateAsNotAFirstTimeUser()
        startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
        finish()
    }

    private fun saveStateAsNotAFirstTimeUser() {
        SharedPreferencesHelper.write(PrefConstant.ON_BOARDED_SUCCESSFULLY, true)
    }

    override fun onBackClickTwo() {
        viewPagerOnBoarding.currentItem = 0
    }
}
