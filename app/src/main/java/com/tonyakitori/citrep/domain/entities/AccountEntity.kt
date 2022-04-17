package com.tonyakitori.citrep.domain.entities

data class AccountEntity(
    val userId: String = "unique()",
    val email: String,
    val password: String,

    val name: String = ""
)