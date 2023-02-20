package com.example.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtils {
    val gson: Gson = GsonBuilder().create()
}