package com.example.amshop.activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.MyRecyclerViewAdaptar
import com.example.amshop.app.Config
import com.example.amshop.app.Endpoint
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.Category
import com.example.amshop.model.CategoryResponse
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.android.synthetic.main.category_activity_content.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.top_nav_bar.*

class CategoryListActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var myListAdapter: MyRecyclerViewAdaptar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    var textViewCartCount: TextView? = null
    var dbHelper = CartDBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)
        init()
    }

    private fun init() {
        setupTopNavBar()
        getCategories()

        drawerLayout = drawer_layout
        navView = nav_view

        var headerView = navView.getHeaderView(0)

        var sessionManager = SessionManager(this)
        if (sessionManager.isLoggedIn()) {
            headerView.header_text_view_name.text = sessionManager.getUser()
            headerView.header_text_view_email.text = sessionManager.getUserEmail()
        } else {
            headerView.header_text_view_name.text = "Guest User"
            headerView.header_text_view_email.text = "Email here"
        }

        myListAdapter = MyRecyclerViewAdaptar(this)
        category_recycler_view.adapter = myListAdapter
        category_recycler_view.layoutManager = GridLayoutManager(this, 2)

        navView.setNavigationItemSelectedListener(this)
        var toggle = ActionBarDrawerToggle(this, drawerLayout, top_nav_bar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupTopNavBar() {
        var toolbar = top_nav_bar
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
    }

    override fun onRestart() {
        super.onRestart()
        updateCartCount()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.main_menu), menu)

        var item = menu?.findItem(R.id.cart)
        MenuItemCompat.setActionView(item, R.layout.cart_layout)
        var view = MenuItemCompat.getActionView(item)

        textViewCartCount = view.cart_icon_text_view
        view.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
        updateCartCount()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            R.id.profile -> Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            R.id.cart -> startActivity(Intent(this, CartActivity::class.java))
        }
        return true
    }

    private fun getCategories() {
        var requestQueue = Volley.newRequestQueue(this)

        var request =
            StringRequest(Request.Method.GET, Endpoint.getCategoryEndpoint(), {
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                myListAdapter.updateData(categoryResponse.data as ArrayList<Category>)

            },
                {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
        requestQueue.add(request)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_account -> startActivity(Intent(this, ProfileActivity::class.java))
            R.id.item_billing -> Toast.makeText(this, "Billing", Toast.LENGTH_LONG).show()
            R.id.item_help -> Toast.makeText(this, "Help", Toast.LENGTH_LONG).show()
            R.id.item_order -> startActivity(Intent(this, OrderHistoryActivity::class.java))
            R.id.item_rate_app -> Toast.makeText(this, "Rate", Toast.LENGTH_LONG).show()
            R.id.item_refer -> Toast.makeText(this, "Refer", Toast.LENGTH_LONG).show()
        }
        return true
    }

    private fun updateCartCount()
    {
        var count = dbHelper.getNumberOfProducts()
        textViewCartCount?.visibility = View.GONE
        if (count == 0)
        {
            textViewCartCount?.visibility = View.GONE
        }
        else
        {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }
}