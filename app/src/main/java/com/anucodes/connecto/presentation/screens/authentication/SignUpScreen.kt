package com.anucodes.connecto.presentation.screens.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.anucodes.connecto.core.authentication.models.AuthState
import com.anucodes.connecto.core.authentication.models.UserInfo
import com.anucodes.connecto.core.authentication.viewmodel.AuthViewModel
import com.anucodes.connecto.ui.theme.AppColors


@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val fontColor = if (isSystemInDarkTheme()) AppColors.FontPrimaryDark else AppColors.FontPrimaryLight
    val authBg = if(isSystemInDarkTheme()) AppColors.AuthBgDark else AppColors.AuthBg
    val darkTheme = isSystemInDarkTheme()

    val authState by authViewModel.authState.collectAsState()

    when(authState){
        is AuthState.Success->{
            navController.navigate("login_screen")
            authViewModel.updateAuthState()
            Toast.makeText(LocalContext.current, "Confirmation email sent!", Toast.LENGTH_SHORT).show()
        }
        is AuthState.Failure->{
            Toast.makeText(LocalContext.current, (authState as AuthState.Failure).message, Toast.LENGTH_SHORT).show()
            authViewModel.updateAuthState()
        }
        is AuthState.Idle->{}
        is AuthState.Loading->{}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = authBg
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Sign up with email!",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = fontColor
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                text = "Get chatting with friends and family today by signing up for our chat app",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = fontColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.82f),
                text = "Your name",
                fontSize = 15.sp,
                color = fontColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                value = name,
                onValueChange = {
                    name = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Password",
                        tint = fontColor
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your name",
                        fontSize = 16.sp,
                        color = if (darkTheme) AppColors.FontHintDark else AppColors.FontHintLight
                    )
                },
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.82f),
                text = "Your email",
                fontSize = 15.sp,
                color = fontColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                value = email,
                onValueChange = {
                    email = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Password",
                        tint = fontColor
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your email",
                        fontSize = 16.sp,
                        color = if (darkTheme) AppColors.FontHintDark else AppColors.FontHintLight
                    )
                },
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.82f),
                text = "Password",
                fontSize = 15.sp,
                color = fontColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                value = password,
                onValueChange = {
                    password = it
                },
                visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = "Password",
                        tint = fontColor
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your password",
                        fontSize = 16.sp,
                        color = if (darkTheme) AppColors.FontHintDark else AppColors.FontHintLight
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Password Visibility",
                            tint = fontColor
                        )
                    }
                },
                shape = RoundedCornerShape(20.dp)
            )

        }

        Spacer(modifier = Modifier.height(100.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                enabled = if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) true else false,
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (darkTheme) AppColors.ButtonPrimary else AppColors.ButtonSecondary,
                    contentColor = if (darkTheme) AppColors.FontPrimaryDark else AppColors.FontPrimaryLight
                ),
                onClick = {
                    val userInfo = UserInfo(
                        name = name,
                        username = "",
                        email = email
                    )
                    authViewModel.createUserUsingEmailAndPassword(userInfo = userInfo, userPassword = password)
                }
            ) {
                Text(
                    text = "Register!",
                    fontSize = 15.sp,
                    color = fontColor
                )
            }
        }
    }
}

