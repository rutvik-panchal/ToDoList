package com.rutvik.apps.todolist.onboarding


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.rutvik.apps.todolist.R

/**
 * A simple [Fragment] subclass.
 */
class OnBoardingTwoFragment : Fragment() {

    lateinit var textViewBack : TextView
    lateinit var textViewNext : TextView

    lateinit var onClickOnBoardingTwo: OnClickOnBoardingTwo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickOnBoardingTwo = context as OnClickOnBoardingTwo

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        clickListeners()
    }

    private fun clickListeners() {
        textViewBack.setOnClickListener {
            onClickOnBoardingTwo.onBackClickTwo()
        }
        textViewNext.setOnClickListener {
            onClickOnBoardingTwo.onDoneClickTwo()
        }
    }

    private fun bindViews(view: View) {
        textViewBack = view.findViewById(R.id.onBoardingTwoBackButton)
        textViewNext = view.findViewById(R.id.onBoardingTwoDoneButton)
    }

    interface OnClickOnBoardingTwo {
        fun onDoneClickTwo()
        fun onBackClickTwo()
    }
}
