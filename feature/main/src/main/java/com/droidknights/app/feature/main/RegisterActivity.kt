package com.droidknights.app.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class RegisterActivity : AppCompatActivity(), Register1Fragment.OnFragmentInteractionListener, Register2Fragment.OnFragmentInteractionListener, Register3Fragment.OnFragmentInteractionListener, MyPageFragment.OnFragmentInteractionListener {

    private val registrationData = RegistrationData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (savedInstanceState == null) {
            loadFragment(Register1Fragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onRegister1Submit(name: String, phoneNumber: String, birthday: String, location: String, email: String, password: String) {
        registrationData.name = name
        registrationData.phoneNumber = phoneNumber
        registrationData.birthday = birthday
        registrationData.location = location
        registrationData.email = email
        registrationData.password = password

        val register2Fragment = Register2Fragment()
        loadFragment(register2Fragment)
    }

    override fun onRegister2Submit(job: String) {
        registrationData.job = job

        val register3Fragment = Register3Fragment()
        loadFragment(register3Fragment)
    }

    override fun onRegister3Submit(hobby: String) {
        registrationData.hobby = hobby

        // 모든 데이터를 모아서 처리하거나 저장하는 로직 추가
        showMyPage()
    }

    private fun showMyPage() {
        val bundle = Bundle().apply {
            putString("name", registrationData.name)
            putString("phoneNumber", registrationData.phoneNumber)
            putString("birthday", registrationData.birthday)
            putString("location", registrationData.location)
            putString("job", registrationData.job)
            putString("hobby", registrationData.hobby)
            putString("email", registrationData.email)
            putString("password", registrationData.password)
        }

        val myPageFragment = MyPageFragment().apply {
            arguments = bundle
        }

        loadFragment(myPageFragment)
    }

    override fun onEditProfile() {
        // 프로필 수정을 위한 프래그먼트 로드
        loadFragment(Register1Fragment())
    }
}