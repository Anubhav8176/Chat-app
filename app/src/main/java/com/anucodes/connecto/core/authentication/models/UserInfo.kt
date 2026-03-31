package com.anucodes.connecto.core.authentication.models

import kotlinx.serialization.Serializable


@Serializable
data class UserInfo(
    val name: String,
    val email: String,
    val username: String?="",
    val profilePictureUrl: String?=""
)