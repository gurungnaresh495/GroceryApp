package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.MyTabLayoutAdapter
import com.example.amshop.app.Endpoint
import com.example.amshop.model.Category
import com.example.amshop.model.SubCategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.top_nav_bar.*

class SubCategoryActivity : AppCompatActivity() {

    lateinit var myTabLayout: MyTabLayoutAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
    }

    private fun init() {

        var category = this.intent.getSerializableExtra(Category.CATEGORY_KEY) as Category
        setupTopNavBar(category )
        getData(category.catId)
        myTabLayout = MyTabLayoutAdapter(supportFragmentManager)
        sub_category_view_pager.adapter = myTabLayout
        sub_category_tab_layout.setupWithViewPager(sub_category_view_pager)
    }

    private fun setupTopNavBar(category: Category)
    {
        var toolbar = top_nav_bar
        toolbar.title = category.catName

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.main_menu), menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            android.R.id.home -> finish()
            R.id.settings -> Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            R.id.profile -> Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            R.id.cart -> startActivity(Intent(this, CartActivity::class.java))
        }
        return true
    }

    private fun getData(catId: Int) {

        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, Endpoint.getSubCategoryEndpoint(catId), Response.Listener {
            var gson = Gson()
            var subCategoryResponse = gson.fromJson(it, SubCategoryResponse::class.java)
            for (i in subCategoryResponse.data) {
                myTabLayout.addFragment(i)
            }
        },
            Response.ErrorListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
        requestQueue.add(request)
    }
}