package com.afifemwe.bookinggedung.api

import com.afifemwe.bookinggedung.utils.Const
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object {
        private var retrofit: Retrofit? = null

        fun getApi(): Retrofit? {
            if (retrofit == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                retrofit = Retrofit.Builder()
                    .baseUrl(Const.LOCAL_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }

            return retrofit
        }

    }
}