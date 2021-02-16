package com.example.amshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amshop.R
import com.example.amshop.model.Category

class SubCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
    }

    private fun init()
    {
        var category = this.intent.getSerializableExtra(Category.CATEGORY_KEY) as Category
        getData(category.catId)

    }

    private fun getData(catId: Int)
    {

    }
}