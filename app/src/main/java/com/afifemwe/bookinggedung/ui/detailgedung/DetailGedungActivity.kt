package com.afifemwe.bookinggedung.ui.detailgedung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.afifemwe.bookinggedung.R
import com.afifemwe.bookinggedung.databinding.ActivityDetailGedungBinding

class DetailGedungActivity : AppCompatActivity() {

    private lateinit var bind: ActivityDetailGedungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_detail_gedung)


    }
}