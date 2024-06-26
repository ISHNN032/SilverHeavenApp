package com.droidknights.app.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null;

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var joinButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        // EditText와 Button 초기화
        emailEditText = findViewById(R.id.email_edittext)
        passwordEditText = findViewById(R.id.password_edittext)
        loginButton = findViewById(R.id.login_button)
        joinButton = findViewById(R.id.join_button)

        // 로그인 버튼 클릭 리스너 설정
        loginButton.setOnClickListener {
            signinAndSignup()
        }

        joinButton.setOnClickListener {
            moveRegisterPage()
        }
    }

    fun signinAndSignup() {

        auth?.signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
            ?.addOnCompleteListener {
                task ->
                if(task.isSuccessful) {
                    Toast.makeText(this,"로그인 완료", Toast.LENGTH_LONG).show()
                    moveMainPage(task.result.user)
                }else if(task.exception?.message.isNullOrEmpty()) {
                    Toast.makeText(this,task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

//
//    fun signinEmail() {
//        auth?.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
//            ?.addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful) {
//                    moveMainPage(task.result.user)
//                }else if(task.exception?.message.isNullOrEmpty()) {
//                    Toast.makeText(this,task.exception?.message, Toast.LENGTH_LONG).show()
//                }
//            }
//    }

    fun moveMainPage(user: FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun moveRegisterPage() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}