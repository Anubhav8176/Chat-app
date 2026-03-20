package com.anucodes.connecto.core.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anucodes.connecto.core.authentication.models.AuthState
import com.anucodes.connecto.core.authentication.models.LogInRequest
import com.anucodes.connecto.core.authentication.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val supabaseAuth: Auth
): ViewModel() {
    val TAG = "Authentication:"

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun createUserUsingEmailAndPassword(userInfo: UserInfo, userPassword: String){
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val user = supabaseAuth.signUpWith(Email){
                    email = userInfo.email
                    password = userPassword
                    data = buildJsonObject {
                        put("name", userInfo.name)
                        put("username", userInfo.username)
                    }
                }

            }catch (e: Exception){
                Log.e(TAG, "Failed to creating using email and password because ${e.message}")
            }finally {
                _authState.value = AuthState.Idle
            }
        }
    }

    fun signInWithEmailAndPassword(logInRequest: LogInRequest){
        viewModelScope.launch {
            try {
                val user = supabaseAuth.signInWith(Email){
                    email = logInRequest.email
                    password = logInRequest.password
                }
            }catch (e: Exception){
                Log.e(TAG, "Failed to login using email and password because ${e.message}")
            }
        }

    }

}