package com.afifemwe.bookinggedung.ui.main.customer.riwayat.adapter

import android.content.Context
import android.graphics.Color.red
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afifemwe.bookinggedung.R
import com.afifemwe.bookinggedung.databinding.LayoutItemBookingBinding
import com.afifemwe.bookinggedung.databinding.LayoutItemExploreGedungBinding
import com.afifemwe.bookinggedung.ui.main.customer.riwayat.response.ListRiwayatBookingItem
import com.afifemwe.bookinggedung.ui.main.pemilik.beranda.response.ListGedungItem
import com.afifemwe.bookinggedung.utils.Const
import com.afifemwe.bookinggedung.utils.Converter
import com.bumptech.glide.Glide
import java.util.*


class RiwayatBookingAdapter(
    private var listRiwayatBooking: List<ListRiwayatBookingItem>,
    context: Context
) : RecyclerView.Adapter<RiwayatBookingAdapter.ViewHolder>() {

    private var onItemItemClickCallback: OnItemClickCallback? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RiwayatBookingAdapter.ViewHolder {
        val bind = LayoutItemBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listRiwayatBooking[position])
    }

    override fun getItemCount(): Int = listRiwayatBooking.size

    inner class ViewHolder(private val bind: LayoutItemBookingBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(booking: ListRiwayatBookingItem) {
            bind.apply {
                Log.i("Booking Data", booking.toString())
                tvItemNamaGedung.text = booking.namaGedung
                tvItemCustomer.text = booking.namaCustomer
                tvTanggalSewa.text = booking.tanggalSewa
                tvStatus.text = booking.status

                when(booking.status) {
                    "Menunggu Pembayaran" -> {
                        tvStatus.setBackgroundColor(this.root.resources.getColor(R.color.orange))
                    }

                    "Batal" -> {
                        tvStatus.setBackgroundColor(this.root.resources.getColor(R.color.gray))
                    }

                    "Sudah Bayar" -> {
                        tvStatus.setBackgroundColor(this.root.resources.getColor(R.color.dark_lime))
                    }
                }

                itemView.setOnClickListener { onItemItemClickCallback?.onItemClicked(booking) }
            }
        }
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemItemClickCallback = onItemClickCallback
    }



    interface OnItemClickCallback {
        fun onItemClicked(data: ListRiwayatBookingItem)
    }
}