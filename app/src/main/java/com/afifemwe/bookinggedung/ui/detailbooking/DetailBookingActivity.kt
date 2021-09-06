package com.afifemwe.bookinggedung.ui.detailbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afifemwe.bookinggedung.R

class DetailBookingActivity : AppCompatActivity() {
    companion object {
        const val ID_BOOKING_KEY = "id_booking_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_booking)
    }
}