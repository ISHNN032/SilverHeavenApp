package com.droidknights.app.feature.main

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.Calendar

class Register1Fragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var datePickerBirthday: DatePicker
    private lateinit var editTextLocation: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    private var listener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener {
        fun onRegister1Submit(name: String, phoneNumber: String, birthday: String, location: String, email: String, password: String)
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
        val view = inflater.inflate(R.layout.register_sub1, container, false)



        editTextName = view.findViewById(R.id.editTextName)
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        datePickerBirthday = view.findViewById(R.id.datePicker)
        editTextLocation = view.findViewById(R.id.editTextLocation)
        buttonSubmit = view.findViewById(R.id.buttonSubmit)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)

        buttonSubmit.setOnClickListener {
            val name = editTextName.text.toString()
            val phoneNumber = editTextPhoneNumber.text.toString()
            val day = datePickerBirthday.dayOfMonth
            val month = datePickerBirthday.month + 1
            val year = datePickerBirthday.year
            val birthday = "$year-$month-$day"
            val location = editTextLocation.text.toString()
            val email = editTextEmail.text.toString();
            val password = editTextPassword.text.toString();

            listener?.onRegister1Submit(name, phoneNumber, birthday, location, email, password)
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}