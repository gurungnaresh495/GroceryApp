package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.Category
import com.example.amshop.model.CategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.top_nav_bar.*

class CategoryListActivity : AppCompatActivity() {

    lateinit var myListAdapter : MyRecyclerViewAdaptar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)
        init()
    }

    private fun init()
    {
        setupTopNavBar()
        getCategories()
        myListAdapter = MyRecyclerViewAdaptar(this)
        category_recycler_view.adapter = myListAdapter
        category_recycler_view.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setupTopNavBar()
    {
        var toolbar = top_nav_bar
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.main_menu), menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.settings -> Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            R.id.profile -> Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            R.id.cart -> startActivity(Intent(this, CartActivity::class.java))
        }
        return true
    }
    private fun getCategories()
    {
        var requestQueue = Volley.newRequestQueue(this)

        var request = StringRequest(Request.Method.GET, Endpoint.getCategoryEndpoint(), Response.Listener {
            var gson = Gson()
            var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
            myListAdapter.updateData(categoryResponse.data as ArrayList<Category>)

        },
            Response.ErrorListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
        requestQueue.add(request)
    }
}