package com.example.testapp.dataModel

import com.google.gson.annotations.SerializedName

data class TestData(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val header: String,
    @SerializedName("body")
    val message: String
)