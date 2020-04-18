package com.rutvik.apps.todolist.onboarding


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
class OnBoardingOneFragment : Fragment() {

    lateinit var textViewNext : TextView
    lateinit var onNextClick: OnClickOnBoardingOne

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextClick = context as OnClickOnBoardingOne
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        clickListeners()
    }

    private fun clickListeners() {
        textViewNext.setOnClickListener {
            onNextClick.onNextClickOne()
        }
    }

    private fun bindViews(view: View) {
        textViewNext = view.findViewById(R.id.onBaordingOneNextButton)
    }

    interface OnClickOnBoardingOne {
        fun onNextClickOne()
    }

}
