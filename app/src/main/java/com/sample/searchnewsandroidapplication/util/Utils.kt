package com.sample.searchnewsandroidapplication.util

import okhttp3.ResponseBody
import org.json.JSONObject

object Utils {

    @JvmStatic
    fun getErrorMessage(responseBody: ResponseBody?): String? {
        return try {
            val jsonObject = JSONObject(responseBody?.string())
            jsonObject.getString("message")
        } catch (ignored: Exception) {
            null
        }
    }
}