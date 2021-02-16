package com.example.amshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.MyRecyclerViewAdaptar
import com.example.amshop.model.Category
import com.example.amshop.model.CategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_category_list.*

class CategoryListActivity : AppCompatActivity() {

    lateinit var myListAdapter : MyRecyclerViewAdaptar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)
        init()
    }

    private fun init()
    {
        getCategories()
        myListAdapter = MyRecyclerViewAdaptar(this)
        category_recycler_view.adapter = myListAdapter
        category_recycler_view.layoutManager = GridLayoutManager(this, 2)
    }

    private fun getCategories()
    {
        var url = "https://grocery-second-app.herokuapp.com/api/category"
        var requestQueue = Volley.newRequestQueue(this)

        var request = StringRequest(Request.Method.GET, url, Response.Listener {
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