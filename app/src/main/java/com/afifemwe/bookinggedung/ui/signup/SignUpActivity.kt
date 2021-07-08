package com.afifemwe.bookinggedung.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.afifemwe.bookinggedung.R
import com.afifemwe.bookinggedung.databinding.ActivitySignUpBinding
import com.afifemwe.bookinggedung.utils.ValidateInput

class SignUpActivity : AppCompatActivity() {

    private lateinit var bind: ActivitySignUpBinding
    private lateinit var tipePenggunaList: MutableList<String>

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        initSpinnerPengguna()

        bind.btnSignUp.setOnClickListener { registAccount() }

    }

    private fun registAccount() {
        bind.apply {

            if (ValidateInput.validate(etUsername)) username = etUsername.text.toString()
            if (ValidateInput.validate(etEmail)) email = etEmail.text.toString()
            if (ValidateInput.validate(etPassword)) password = etPassword.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Check Email
                // Save Data To Database

            }
        }
    }

    private fun initSpinnerPengguna() {
        tipePenggunaList = getTipePenggunaList()

        var adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipePenggunaList)

        bind.spinnerTipePengguna.setAdapter(adapter)

        Log.i("ListPg", tipePenggunaList.toString())
    }

    private fun getTipePenggunaList(): MutableList<String> {
        val pengguna: ArrayList<String> = ArrayList()

        pengguna.add(0,"Pemilik")
        pengguna.add(1,"Customer")

        return pengguna
    }
}