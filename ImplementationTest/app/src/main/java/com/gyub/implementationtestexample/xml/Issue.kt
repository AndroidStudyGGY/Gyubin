package com.gyub.implementationtestexample.xml

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * @author   Gyub
 * @created  2024/04/16
 */
data class Issue(
    @SerializedName("id")
    val id: Long,
    @SerializedName("number")
    val num: Int,
    @SerializedName("title")
    val title: String
)
