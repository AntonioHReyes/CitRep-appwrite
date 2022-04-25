package com.tonyakitori.citrep.framework.domain

import com.google.gson.annotations.SerializedName
import com.tonyakitori.citrep.domain.entities.PostEntity

data class PostAppWrite(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("image1")
    val image1FileId: String = "",
    @SerializedName("image2")
    val image2FileId: String = "",
    @SerializedName("image3")
    val image3FileId: String = "",
    @SerializedName("enabled")
    val enabled: Boolean = true,
    @SerializedName("date")
    val date: Long
)

fun PostEntity.toPostAppWrite() = PostAppWrite(
    userId = userId,
    userName = userName,
    comment = comment,
    image1FileId = image1FileId,
    image2FileId = image2FileId,
    image3FileId = image3FileId,
    enabled = enabled,
    date = date
)