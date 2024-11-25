package com.jesse.c24klpaging3.data.model

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Result>
)