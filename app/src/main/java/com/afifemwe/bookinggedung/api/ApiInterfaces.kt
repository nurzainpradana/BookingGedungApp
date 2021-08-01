package com.afifemwe.bookinggedung.api

import com.afifemwe.bookinggedung.model.GeneralResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterfaces {

    /* Registrasi Akun Baru */
    @FormUrlEncoded
    @POST("registrasi_akun")
    fun registerAkun(
        @Field("name") nama: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("no_hp") no_hp: String,
        @Field("jenis_kelamin") jenis_kelamin: String,
        @Field("alamat") alamat: String,
        @Field("tipe_user") tipe_user: String,
        @Field("no_rek") no_rek: String?,
        @Field("nama_bank") nama_bank: String?,
        @Field("nama_pemilik") nama_pemilik: String?
    ): Call<GeneralResponse>

    /* Check Login */
    @FormUrlEncoded
    @POST("check_login")
    fun checkLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("tipe_user") tipe_user: String,
    ): Call<GeneralResponse>

    /* Upload Photo Gedung */
    @FormUrlEncoded
    @POST("upload_photo")
    fun uploadPhoto(
        @Field("imgPath") imgPath: String?,
        @Field("imgName") imgName: String?
    ): Call<GeneralResponse>


    /* Registrasi Gedung Baru */
    @FormUrlEncoded
    @POST("buat_gedung_baru")
    fun buatGedungBaru(
        @Field("nama") nama: String,
        @Field("gambar") gambar: String,
        @Field("kapasitas") kapasitas: String,
        @Field("fasilitas") fasilitas: String,
        @Field("jam_operasional") jam_operasional: String,
        @Field("maps") maps: String,
        @Field("harga") harga: String,
        @Field("pemillik") pemilik: String
    ): Call<GeneralResponse>
}