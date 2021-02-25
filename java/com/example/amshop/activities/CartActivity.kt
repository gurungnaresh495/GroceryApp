package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amshop.R
import com.example.amshop.adapter.CartListAdapter
import com.example.amshop.helper.CartDBHelper
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), CartListAdapter.CartListButtonListener {

    lateinit var cartListAdapter: CartListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
    }

    private fun init() {
        cartListAdapter = CartListAdapter(this, CartDBHelper(this).getAllProducts())
        cart_list_recyler_view.adapter = cartListAdapter
        cart_list_recyler_view.layoutManager = LinearLayoutManager(this)
        updateSummary()
        cart_checkout_button.setOnClickListener{
            startActivity(Intent(this, AddressActivity::class.java))
        }

    }
    private fun updateSummary()
    {
        cartListAdapter.calculateSubtotal()
        cart_text_viewsub_total.text = cartListAdapter.subtotal.toString()
        cart_text_view_discount.text = cartListAdapter.discount.toString()
        cart_text_views_delivery_charges.text = (0.00).toString()
        cart_text_view_total.text = (cartListAdapter.subtotal - cartListAdapter.discount).toString()
    }

    override fun onClickButtonCarList() {
        updateSummary()
    }
}