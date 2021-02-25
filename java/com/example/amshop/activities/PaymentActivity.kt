package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amshop.R
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }

    private fun init() {
        pay_now_button.setOnClickListener {
            startActivity(Intent(this, OrderConfirmationActivity::class.java))
        }
    }
}