package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.amshop.R
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.model.Category
import com.example.amshop.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.cart_list_row.view.*
import kotlinx.android.synthetic.main.increase_text_decrease.*
import kotlinx.android.synthetic.main.top_nav_bar.*


class ProductDetailsActivity : AppCompatActivity() {
    var dbHelper = CartDBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        init()
    }

    private fun init()
    {
        var product = intent.getSerializableExtra(Product.PRODUCT_KEY) as Product
        setupTopNavBar(product)
        details_activity_product_name.text = product.productName
        details_activity_product_price.text = "$" + product.price.toString()
        details_activity_product_description.movementMethod = ScrollingMovementMethod()
        details_activity_product_description.text = product.description
        Picasso.get().load("https://rjtmobile.com/grocery/images/${product.image}").into(details_activity_image_view)

        if (dbHelper.getQuantityOfProduct(product)> 0)
        {
            product_details_add_to_cart_text_views.visibility = View.VISIBLE
            details_activity_add_to_cart_button.visibility = View.GONE
        }
        else
        {
            product_details_add_to_cart_text_views.visibility = View.GONE
        }

        product_count.text = dbHelper.getQuantityOfProduct(product).toString()

        details_activity_add_to_cart_button.setOnClickListener{
            dbHelper.addProduct(product)
            product_details_add_to_cart_text_views.visibility = View.VISIBLE
            details_activity_add_to_cart_button.visibility = View.GONE
            product_count.text = dbHelper.getQuantityOfProduct(product).toString()
        }

        increase_button.setOnClickListener{
            dbHelper.increaseProduct(product)
            product_count.text = dbHelper.getQuantityOfProduct(product).toString()
        }

        decrease_button.setOnClickListener{
            dbHelper.decreaseProduct(product)
            product_count.text = dbHelper.getQuantityOfProduct(product).toString()
            if (CartDBHelper(this).getQuantityOfProduct(product)== 0)
            {
                product_details_add_to_cart_text_views.visibility = View.GONE
                details_activity_add_to_cart_button.visibility = View.VISIBLE
            }
        }

    }
    private fun setupTopNavBar(product: Product)
    {
        var toolbar = top_nav_bar
        toolbar.title = product.productName
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
}