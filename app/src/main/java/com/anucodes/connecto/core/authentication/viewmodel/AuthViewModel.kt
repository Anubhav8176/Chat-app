package com.anucodes.connecto.core.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anucodes.connecto.core.authentication.models.AuthState
import com.anucodes.connecto.core.authentication.models.LogInRequest
import com.anucodes.connecto.core.authentication.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val supabaseAuth: Auth,
    private val supabase: SupabaseClient
): ViewModel() {
    val TAG = "Authentication:"

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<UserInfo?>(null)
    val currentUser = _currentUser.asStateFlow()


    init {
        getCurrentUserInfo()
    }

    fun createUserUsingEmailAndPassword(userInfo: UserInfo, userPassword: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                supabaseAuth.signUpWith(Email) {
                    email = userInfo.email
                    password = userPassword
                    data = buildJsonObject {
                        put("name", userInfo.name)
                        put("username", userInfo.email.substringBefore("@"))
                        put("avatar_url", userInfo.profilePictureUrl ?: "")
                    }
                }

                _authState.value = AuthState.Success

            } catch (e: Exception) {
                _authState.value = AuthState.Failure("Signup failed. Please try again.")
                Log.e(TAG, "Signup failed: ${e.message}")
            }
        }
    }

    fun signInWithEmailAndPassword(logInRequest: LogInRequest) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                supabaseAuth.signInWith(Email) {
                    email = logInRequest.email
                    password = logInRequest.password
                }
                val session = supabaseAuth.sessionStatus.first {
                    it is SessionStatus.Authenticated || it is SessionStatus.NotAuthenticated
                }
                when (session) {
                    is SessionStatus.Authenticated -> {
                        getCurrentUserInfo()
                        _authState.value = AuthState.Success
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _authState.value = AuthState.Failure("Please confirm your email to login!")
                    }
                    else -> {
                        _authState.value = AuthState.Failure("Couldn't authenticate user.")
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Failure(e.message.toString())
                Log.e(TAG, "Failed to login: ${e.message}")
            }
        }
    }

    fun updateAuthState(){
        _authState.value = AuthState.Idle
    }

    fun signOutCurrentUser() {
        viewModelScope.launch {
            try {
                supabaseAuth.signOut()
                _currentUser.value = null
                _authState.value = AuthState.Idle
            } catch (e: Exception) {
                Log.e(TAG, "Sign out failed: ${e.message}")
            }
        }
    }

    fun getCurrentUserInfo() {
        val userInfoRes = supabaseAuth.currentUserOrNull()
        if (userInfoRes != null) {
            val metadata = userInfoRes.userMetadata
            val userInfo = UserInfo(
                name     = metadata?.get("name")?.jsonPrimitive?.contentOrNull ?: "",
                username = metadata?.get("username")?.jsonPrimitive?.contentOrNull ?: "",
                email    = userInfoRes.email ?: ""
            )
            _currentUser.value = userInfo
        } else {
            _currentUser.value = null
        }
    }
}
