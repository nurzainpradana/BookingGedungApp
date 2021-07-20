package com.afifemwe.bookinggedung.ui.cekagenda

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.afifemwe.bookinggedung.R
import com.afifemwe.bookinggedung.databinding.ActivityCekAgendaBinding
import java.text.SimpleDateFormat
import java.util.*

class CekAgendaActivity : AppCompatActivity() {

    private lateinit var bind: ActivityCekAgendaBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_cek_agenda)

        bind.cbTime1.apply {
//            paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            isChecked = true
            isEnabled = false
            text = "$text ( Reserved )"
        }

        bind.btnChangeDate.setOnClickListener { getDatePicker() }
    }

    private fun getDatePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val c = Calendar.getInstance()
            c.timeZone = TimeZone.getDefault()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val formatDate = "E, dd MMMM yyyy"
            val sdf = SimpleDateFormat(formatDate, Locale.getDefault())
            val myCalendar = Calendar.getInstance(Locale.getDefault())

            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    bind.tvDate.text = sdf.format(myCalendar.time)
                },
                year, month, day
            ).show()
        }
    }
}