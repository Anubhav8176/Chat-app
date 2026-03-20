package com.anucodes.connecto.core.authentication.models

sealed class AuthState {
    object Idle: AuthState()
    object Loading: AuthState()
    object Success: AuthState()
    data class Failure(val message: String): AuthState()
}