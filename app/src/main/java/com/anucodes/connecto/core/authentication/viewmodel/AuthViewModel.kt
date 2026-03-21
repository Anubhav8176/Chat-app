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
import io.github.jan.supabase.auth.status.SessionStatus
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

    private val _currentUser = MutableStateFlow<UserInfo?>(null)
    val currentUser = _currentUser.asStateFlow()


    init {
        getCurrentUserInfo()
    }

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
                _authState.value = AuthState.Success

            }catch (e: Exception){
                _authState.value = AuthState.Failure("Can't authenticate ")
                Log.e(TAG, "Failed to creating using email and password because ${e.message}")
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
                supabaseAuth.sessionStatus.collect {authRes->
                    when(authRes){
                        is SessionStatus.Authenticated->{
                            _authState.value = AuthState.Success
                        }
                        is SessionStatus.NotAuthenticated->{
                            _authState.value = AuthState.Failure("Please confirm the email to login!")
                        }
                        else -> {
                            _authState.value = AuthState.Failure("Couldn't authenticate user.")
                        }
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "Failed to login using email and password because ${e.message}")
            }
        }

    }

    fun updateAuthState(){
        _authState.value = AuthState.Idle
    }

    fun signOutCurrentUser(){
        viewModelScope.launch {
            supabaseAuth.signOut()
        }
    }

    fun getCurrentUserInfo(){
        val userInfoRes = supabaseAuth.currentUserOrNull()

        if (userInfoRes!=null){
            val userInfo = UserInfo(
                name = userInfoRes.userMetadata?.get("name").toString(),
                username = userInfoRes.userMetadata?.get("username").toString(),
                email = userInfoRes.userMetadata?.get("email").toString()
            )

            _currentUser.value = userInfo
        }else{
            _currentUser.value = null
        }

        Log.i(TAG, "UserInfo => ${_currentUser.value}")
    }
}
