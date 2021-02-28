package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.amshop.R
import com.example.amshop.helper.CartDBHelper
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_base.drawer_layout
import kotlinx.android.synthetic.main.activity_base.nav_view
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.cart_layout.*
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.android.synthetic.main.top_nav_bar.*

abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var dbHelper: CartDBHelper
    abstract val contentResource : Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        var view = LayoutInflater.from(this).inflate(contentResource, drawer_layout, false)
        var bar = LayoutInflater.from(this).inflate(R.layout.top_nav_bar, drawer_layout, false)
        dbHelper = CartDBHelper(this)
        drawerLayout = drawer_layout
        drawerLayout.addView(view, 0)
        var navView = nav_view
        if (view is LinearLayout)
        {
            view.addView(bar, 0)
        }

        navView.setNavigationItemSelectedListener (this)

        var toolbar = top_nav_bar
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.main_menu), menu)
        var item = menu?.findItem(R.id.cart)
        MenuItemCompat.setActionView(item, R.layout.cart_layout)
        var view = MenuItemCompat.getActionView(item)
        var textViewCartCount = view.cart_icon_text_view
        view.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }

        var count = dbHelper.getNumberOfProducts()
        if (count == 0)
        {
            textViewCartCount?.visibility = View.GONE
        }
        else
        {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_account -> Toast.makeText(this, "Account", Toast.LENGTH_LONG).show()
            R.id.item_billing -> Toast.makeText(this, "Billing", Toast.LENGTH_LONG).show()
            R.id.item_help -> Toast.makeText(this, "Help", Toast.LENGTH_LONG).show()
            R.id.item_order -> startActivity(Intent(this, OrderHistoryActivity::class.java))
            R.id.item_rate_app -> Toast.makeText(this, "Rate", Toast.LENGTH_LONG).show()
            R.id.item_refer -> Toast.makeText(this, "Refer", Toast.LENGTH_LONG).show()
        }
        return true
    }

}