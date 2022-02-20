package com.example.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.Date

object GsonUtils {

    val gson: Gson = GsonBuilder()
        //.registerTypeAdapter(JsonItemType::class.java, JsonItemTypeDeserializer)
        .create()
}