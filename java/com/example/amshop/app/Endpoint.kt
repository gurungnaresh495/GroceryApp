package com.example.amshop.app

import java.util.*

class Endpoint {

    companion object{
        private const val CATEGORY = "category"
        private const val SUB_CATEGORY = "subcategory/"
        private const val PRODUCT = "products/sub/"
        private const val LOGIN = "auth/login"
        private const val REGISTER = "auth/register"

        fun getCategoryEndpoint() : String
        {
            return Config.BASE_URL + CATEGORY
        }

        fun getSubCategoryEndpoint( catId: Int): String
        {
            return Config.BASE_URL+ SUB_CATEGORY + catId.toString()
        }

        fun getProductEndpoint( subCatId: Int): String
        {
            return Config.BASE_URL + PRODUCT + subCatId.toString()
        }

        fun getLoginEndpoint(): String
        {
            return Config.BASE_URL + LOGIN
        }

        fun getRegisterEndpoint(): String
        {
            return Config.BASE_URL + REGISTER
        }
    }
}