package com.rutvik.apps.todolist.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingOneFragment()
            else -> OnBoardingTwoFragment() //If position is 1
        }
    }

    override fun getCount(): Int {
        return 2
     }

}