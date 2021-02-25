package com.example.amshop.adapter

import android.content.Context
import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amshop.R
import com.example.amshop.model.*
import kotlinx.android.synthetic.main.row_layout_address.view.*

class AddressRecyclerViewAdapter(var context: Context) : RecyclerView.Adapter<AddressRecyclerViewAdapter.MyViewHolder>(){

    private var addressList = ArrayList<com.example.amshop.model.Address>()

    inner class MyViewHolder(var view: View): RecyclerView.ViewHolder(view)
    {
        fun bind(address: com.example.amshop.model.Address)
        {
            view.row_layout_address_street.text = address.houseNo + " " + address.streetName
            view.row_layout_address_city.text = address.city
            view.row_layout_address_pin_code.text = address.pincode.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_address, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(addressList[position])
    }

    override fun getItemCount(): Int {
        return this.addressList.size
    }

    fun updateAddressList(list: ArrayList<com.example.amshop.model.Address>)
    {
        this.addressList = list
        notifyDataSetChanged()
    }
}