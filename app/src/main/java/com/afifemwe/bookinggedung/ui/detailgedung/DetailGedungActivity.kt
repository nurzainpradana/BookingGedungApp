package com.afifemwe.bookinggedung.ui.detailgedung

import android.app.Application
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.afifemwe.bookinggedung.R
import com.afifemwe.bookinggedung.api.Api
import com.afifemwe.bookinggedung.api.ApiInterfaces
import com.afifemwe.bookinggedung.databinding.ActivityDetailGedungBinding
import com.afifemwe.bookinggedung.model.Fasilitas
import com.afifemwe.bookinggedung.ui.cekagenda.CekAgendaActivity
import com.afifemwe.bookinggedung.ui.cekagenda.CekAgendaActivity.Companion.HARGA_SEWA_KEY
import com.afifemwe.bookinggedung.ui.cekagenda.CekAgendaActivity.Companion.TANGGAL_SEWA_KEY
import com.afifemwe.bookinggedung.ui.checkout.CheckoutActivity.Companion.NAMA_GEDUNG_KEY
import com.afifemwe.bookinggedung.ui.detailgedung.adapter.FasilitasListAdapter
import com.afifemwe.bookinggedung.ui.detailgedung.response.GedungDetailResponse
import com.afifemwe.bookinggedung.ui.main.customer.beranda.adapter.PromoListAdapter
import com.afifemwe.bookinggedung.ui.main.pemilik.beranda.response.GetGedungListResponse
import com.afifemwe.bookinggedung.utils.Const
import com.afifemwe.bookinggedung.utils.Converter
import com.afifemwe.bookinggedung.utils.NetworkUtility
import com.afifemwe.bookinggedung.utils.UserPreference
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailGedungActivity : AppCompatActivity() {

    companion object {
        const val ID_GEDUNG_KEY = "id_gedung_key"
    }

    private lateinit var bind: ActivityDetailGedungBinding

    private lateinit var idGedung: String
    private lateinit var idUser: String

    lateinit var fasilitas: Fasilitas


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_detail_gedung)

        val userPref = UserPreference(this)
        idUser = userPref.getUsername().toString()

        val tipePengguna = userPref.getTipePengguna().toString()
        initView(tipePengguna)

        idGedung = intent.getStringExtra(ID_GEDUNG_KEY).toString()

        initDetailGedung()
//        initRvFasilitas()

        bind.swipeContainer.setOnRefreshListener {
            initDetailGedung()
            bind.swipeContainer.isRefreshing = false
        }

        bind.btnCekAgenda.setOnClickListener {
            getDatePicker()
        }
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

            var selectedDate = ""

            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    selectedDate = sdf.format(myCalendar.time)

                    val i = Intent(this@DetailGedungActivity, CekAgendaActivity::class.java)
                    i.putExtra(ID_GEDUNG_KEY, idGedung)
                    i.putExtra(TANGGAL_SEWA_KEY, selectedDate)
                    startActivity(i)
                },
                year, month, day
            ).show()


        }
    }

    private fun initDetailGedung() {
        if (NetworkUtility.isInternetAvailable(this)) {
            try {
                val service = Api.getApi()!!.create(ApiInterfaces::class.java)
                val call = service.getGedungDetail(idGedung)
                call.enqueue(object : Callback<GedungDetailResponse> {
                    override fun onResponse(
                        call: Call<GedungDetailResponse>,
                        response: Response<GedungDetailResponse>
                    ) {
                        val gedungDetail = response.body()!!.gedungDetail

                        if (gedungDetail != null) {

                            bind.apply {
                                tvNamaGedung.text = gedungDetail.nama
                                tvRating.text = gedungDetail.rating
                                tvKapasitas.text = """Kapasitas ${gedungDetail.kapasitas}"""
                                tvHarga.text = Converter.formatRupiah(gedungDetail.harga)

                                Log.i("cek", gedungDetail.fasilitas.toString())

                                initRvFasilitas(gedungDetail.fasilitas)

                                initButtonMaps(gedungDetail.maps)

                                Glide.with(bind.root)
                                    .load("${Const.PHOTO_URL}${gedungDetail.gambar}")
                                    .error(R.drawable.dummy_image)
                                    .into(ivGambarGedung)

                                if (gedungDetail.maps.isNullOrEmpty() && !gedungDetail.maps?.startsWith("http://")!! && !gedungDetail.maps.startsWith("https://")) {
                                    btnGoToMaps.isClickable = false
                                    btnGoToMaps.visibility = View.GONE
                                }

                                btnGoToMaps.setOnClickListener {
                                    if (!gedungDetail.maps.isNullOrEmpty()) {
                                        val i = Intent(Intent.ACTION_VIEW, Uri.parse(gedungDetail.maps.toString()))
                                        Log.i("maps", gedungDetail.maps.toString())
                                        startActivity(i)
                                    }
                                }

                                btnBooking.setOnClickListener {
                                    val i = Intent(this@DetailGedungActivity, CekAgendaActivity::class.java)
                                    i.putExtra(ID_GEDUNG_KEY, idGedung)
                                    i.putExtra(HARGA_SEWA_KEY, gedungDetail.harga)
                                    i.putExtra(NAMA_GEDUNG_KEY, gedungDetail.nama)
                                    startActivity(i)
                                }


                            }
                        }

                    }

                    override fun onFailure(call: Call<GedungDetailResponse>, t: Throwable) {
                        NetworkUtility.checkYourConnection(this@DetailGedungActivity)
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initButtonMaps(maps: String?) {

    }

    private fun initRvFasilitas(fasilitasRow: String?) {
        if (!fasilitasRow.isNullOrEmpty()) {
            var listFasilitas = mutableListOf<Fasilitas>()

            var listFasilitasGedung = fasilitasRow.split(", ")?.map { it -> it.trim() }



            listFasilitasGedung?.forEach {
                when (it) {
                    "parkir" -> {
                        fasilitas = Fasilitas(id = 1, nama = "Parkir", icon = R.drawable.ic_parkir)
                        listFasilitas.add(fasilitas)
                    }
                    "toilet" -> {
                        fasilitas = Fasilitas(id = 2, nama = "Toilet", icon = R.drawable.ic_toilet)
                        listFasilitas.add(fasilitas)
                    }
                    "musollah" -> {
                        fasilitas = Fasilitas(id = 3, nama = "Musollah", icon = R.drawable.ic_musollah)
                        listFasilitas.add(fasilitas)
                    }
                    "kursi / meja" -> {
                        fasilitas = Fasilitas(id = 4, nama = "Kursi / Meja", icon = R.drawable.ic_meja_kursi)
                        listFasilitas.add(fasilitas)
                    }
                    "ac" -> {
                        fasilitas = Fasilitas(id = 5, nama = "AC", icon = R.drawable.ic_ac)
                        listFasilitas.add(fasilitas)
                    }
                }
            }

            Log.i("listFs", listFasilitasGedung.toString())

            val fasilitasListAdapter = FasilitasListAdapter(listFasilitas, applicationContext)

            bind.rvFasilitas.apply {
                layoutManager = LinearLayoutManager(this@DetailGedungActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = fasilitasListAdapter
            }

        }






//        var fasilitasAdapter = FasilitasListAdapter(context = requireContext(), listPromoPoster = listPromoDummy )
//
//        bind.rvPromo.apply {
//            setHasFixedSize(true)
//            adapter = promoAdapter
//        }


    }

    private fun initView(tipePengguna: String) {
        if (tipePengguna == Const.PEMILIK) {
            bind.apply {
                btnBooking.visibility = View.GONE
            }
        }
    }
}