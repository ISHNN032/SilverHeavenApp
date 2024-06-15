package com.droidknights.app.feature.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class MyPageFragment : Fragment() {
    private lateinit var textViewName: TextView
    private lateinit var textViewPhoneNumber: TextView
    private lateinit var textViewBirthday: TextView
    private lateinit var textViewLocation: TextView
    private lateinit var textViewJob: TextView
    private lateinit var textViewHobby: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPassword: TextView
    private lateinit var buttonEdit: Button

    private var listener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener {
        fun onEditProfile()
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
        val view = inflater.inflate(R.layout.activity_mypage, container, false)

        textViewName = view.findViewById(R.id.textViewName)
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber)
        textViewBirthday = view.findViewById(R.id.textViewBirthday)
        textViewLocation = view.findViewById(R.id.textViewLocation)
        textViewJob = view.findViewById(R.id.textViewJob)
        textViewHobby = view.findViewById(R.id.textViewHobby)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewPassword = view.findViewById(R.id.textViewPassWord)
        buttonEdit = view.findViewById(R.id.buttonEdit)

        arguments?.let {
            textViewName.text = it.getString("name") ?: ""
            textViewPhoneNumber.text = it.getString("phoneNumber") ?: ""
            textViewBirthday.text = it.getString("birthday") ?: ""
            textViewLocation.text = it.getString("location") ?: ""
            textViewJob.text = it.getString("job") ?: ""
            textViewHobby.text = it.getString("hobby") ?: ""
            textViewEmail.text = it.getString("email")?:""
            textViewPassword.text = it.getString("password")?:""
        }

        buttonEdit.setOnClickListener {
            listener?.onEditProfile()
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}