package com.afifemwe.bookinggedung.utils

class Const {
    companion object {
        val PRICE_3_DOWN: String = "Harga < 3 Jt"
        val PRICE_3_UP: String = "Harga > 3 Jt"
        val CAPACITY_500_UP: String = "Kapasitas > 500"
        val CAPACITY_500_DOWN: String = "Kapasitas < 500"


        val SHOW_ALL: String = "SHOW_ALL"
//        const val BASE_URL = "http://localhost/bookinggedung/api/"
        const val BASE_URL = "http://afifemwe.com/bookinggedung/api/"
        const val PHOTO_URL = "http://afifemwe.com/bookinggedung/public/photo_upload/"

        const val PEMILIK = "pemilik"
        const val CUSTOMER = "customer"

        const val SUCCESS_RESPONSE = "success"
        const val FAILED_RESPONSE = "failed"
    }
}