package com.droidknights.app.feature.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment

class Register3Fragment : Fragment() {

    private lateinit var spinnerHobby: Spinner
    private lateinit var buttonSubmit: Button

    private var listener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener {
        fun onRegister3Submit(hobby: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register_sub3, container, false)

        spinnerHobby = view.findViewById(R.id.spinnerHobby)
        buttonSubmit = view.findViewById(R.id.buttonSubmit)

        // Spinner에 표시할 항목 정의
        val hobbyList = arrayOf("독서", "운동", "음악 감상", "영화 감상")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hobbyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHobby.adapter = adapter

        buttonSubmit.setOnClickListener {
            val hobby = spinnerHobby.selectedItem.toString()
            listener?.onRegister3Submit(hobby)
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}