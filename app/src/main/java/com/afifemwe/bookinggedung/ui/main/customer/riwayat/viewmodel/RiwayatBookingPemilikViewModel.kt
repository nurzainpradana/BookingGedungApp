package com.afifemwe.bookinggedung.ui.main.customer.riwayat.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.afifemwe.bookinggedung.api.Api
import com.afifemwe.bookinggedung.api.ApiInterfaces
import com.afifemwe.bookinggedung.ui.main.customer.riwayat.response.ListRiwayatBookingItem
import com.afifemwe.bookinggedung.ui.main.customer.riwayat.response.RiwayatBookingResponse
import com.afifemwe.bookinggedung.ui.main.pemilik.beranda.response.GetGedungListResponse
import com.afifemwe.bookinggedung.ui.main.pemilik.beranda.response.ListGedungItem
import com.afifemwe.bookinggedung.utils.NetworkUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatBookingPemilikViewModel(application: Application) : AndroidViewModel(application) {

    var riwayatBookingList = MutableLiveData<List<ListRiwayatBookingItem>>()

    fun getRiwayatBookingPemilikList(
        context: Context?, username: String
    ) {
        if (NetworkUtility.isInternetAvailable(context!!)) {
            try {
                val service = Api.getApi()!!.create(ApiInterfaces::class.java)
                val call = service.getListRiwayatBookingPemilik(username)
                call.enqueue(object : Callback<RiwayatBookingResponse> {
                    override fun onResponse(
                        call: Call<RiwayatBookingResponse>,
                        response: Response<RiwayatBookingResponse>
                    ) {
                        if (response.body() != null) {
                            riwayatBookingList.postValue(response.body()?.listRiwayatBooking as List<ListRiwayatBookingItem>)
                        } else {
                            NetworkUtility.checkYourConnection(context)
                        }
                    }

                    override fun onFailure(call: Call<RiwayatBookingResponse>, t: Throwable) {
                        NetworkUtility.checkYourConnection(context)
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
                NetworkUtility.checkYourConnection(context)
            }
        } else {
            NetworkUtility.checkYourConnection(context)
        }
    }
}