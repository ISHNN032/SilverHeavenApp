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

class Register2Fragment : Fragment() {

    private lateinit var spinnerJob: Spinner
    private lateinit var buttonSubmit: Button

    private var listener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener {
        fun onRegister2Submit(job: String)
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
        val view = inflater.inflate(R.layout.register_sub2, container, false)

        spinnerJob = view.findViewById(R.id.spinnerJob)
        buttonSubmit = view.findViewById(R.id.buttonSubmit)

        // Spinner에 표시할 항목 정의
        val jobList = arrayOf("사무직", "현장직", "전문직")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, jobList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerJob.adapter = adapter

        buttonSubmit.setOnClickListener {
            val job = spinnerJob.selectedItem.toString()
            listener?.onRegister2Submit(job)
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}