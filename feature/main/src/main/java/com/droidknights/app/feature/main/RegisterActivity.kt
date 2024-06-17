package com.droidknights.app.feature.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.droidknights.app.core.data.repository.DefaultUserRepository
import com.droidknights.app.core.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), Register1Fragment.OnFragmentInteractionListener, Register2Fragment.OnFragmentInteractionListener, Register3Fragment.OnFragmentInteractionListener, MyPageFragment.OnFragmentInteractionListener {
    @Inject
    lateinit var userRepository: DefaultUserRepository
    private val registrationData = RegistrationData()
    var auth : FirebaseAuth? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar)
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

    @OptIn(DelicateCoroutinesApi::class)
    override fun onRegister3Submit(hobby: String) {
        registrationData.hobby = hobby

        val user = User(
            id = "0",
            name = registrationData.name!!,
            phoneNumber = registrationData.phoneNumber!!,
            birthday = registrationData.birthday!!,
            location = registrationData.location!!,
            email = registrationData.email!!,
            password = registrationData.password!!,
            job = registrationData.job!!,
            hobby = registrationData.hobby!!,
            tags = emptyList()
        )

        Log.d("RegisterActivity", user.toString())

        // 모든 데이터를 모아서 처리하거나 저장하는 로직 추가
        GlobalScope.launch {
            Log.d("RegisterActivity", user.toString())
            userRepository.registerUser(user)
        }
        signinAndSignup(registrationData.email!!, registrationData.password!!)
    }

    fun signinAndSignup(email: String, password: String) {
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    //DataManager.setSample(Sample())
                    moveMainPage(task.result.user)
                }else if(task.exception?.message.isNullOrEmpty()) {
                    Toast.makeText(this,task.exception?.message, Toast.LENGTH_LONG).show()
                }else {
                    signinEmail(email, password)
                }
            }
    }

    fun signinEmail(email: String, password: String) {
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    moveMainPage(task.result.user)
                }else if(task.exception?.message.isNullOrEmpty()) {
                    Toast.makeText(this,task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    fun moveMainPage(user: FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onEditProfile() {
        // 프로필 수정을 위한 프래그먼트 로드
        loadFragment(Register1Fragment())
    }
}