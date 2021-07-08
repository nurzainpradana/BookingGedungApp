package com.afifemwe.bookinggedung.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.afifemwe.bookinggedung.R
import com.afifemwe.bookinggedung.databinding.ActivitySignInBinding
import com.afifemwe.bookinggedung.ui.main.customer.CustomerMainActivity
import com.afifemwe.bookinggedung.ui.main.pemilik.PemilikMainActivity
import com.afifemwe.bookinggedung.utils.Const
import com.afifemwe.bookinggedung.utils.CustomToast
import com.afifemwe.bookinggedung.utils.UserPreference
import com.afifemwe.bookinggedung.utils.ValidateInput

class SignInActivity : AppCompatActivity() {

    private lateinit var bind: ActivitySignInBinding
    private lateinit var tipePenggunaList: MutableList<String>

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var tipePengguna: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        initSpinnerTipePengguna()

        bind.btnSignIn.setOnClickListener { verification() }
    }

    private fun initSpinnerTipePengguna() {
        tipePenggunaList = getTipePenggunaList()

        var adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipePenggunaList)

        bind.spinnerTipePengguna.setAdapter(adapter)

        Log.i("ListPg", tipePenggunaList.toString())
    }

    private fun verification() {
        bind.apply {
            username = etUsername.text.toString()
            password = etPassword.text.toString()
            tipePengguna = spinnerTipePengguna.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && tipePengguna.isNotEmpty()) {
                // Check Email
                // Save Data To Database

                val userPref = UserPreference(this@SignInActivity)
                userPref.setUsername(username)
                userPref.setTipePengguna(tipePengguna)

                Toast.makeText(this@SignInActivity, "Berhasil Save UserPreference", Toast.LENGTH_SHORT).show()

                when(tipePengguna) {
                    Const.PEMILIK -> goToMain(CustomerMainActivity::class.java)
                    Const.CUSTOMER -> goToMain(PemilikMainActivity::class.java)
                }
            } else {
                CustomToast.showMessage(this@SignInActivity, "Data Belum Lengkap")
            }
        }
    }

    private fun getTipePenggunaList(): MutableList<String> {
        val pengguna: ArrayList<String> = ArrayList()

        pengguna.add(0,"Pemilik")
        pengguna.add(1,"Customer")

        return pengguna
    }

    fun goToMain(activity: Class<*>) {
        val i = Intent(this, activity)
        startActivity(i)
        finish()
    }
}