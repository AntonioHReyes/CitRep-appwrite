package com.tonyakitori.citrep.domain.entities

data class PostEntity(
    val userId: String,
    val comment: String = "",
    val image1FileId: String = "",
    val image2FileId: String = "",
    val image3FileId: String = "",
    val enabled: Boolean = true
)