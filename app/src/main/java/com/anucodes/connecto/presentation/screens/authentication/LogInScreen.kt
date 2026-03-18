package com.anucodes.connecto.presentation.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anucodes.connecto.R
import com.anucodes.connecto.ui.theme.AppColors
import com.anucodes.connecto.ui.theme.ConnectoTheme


@Composable
fun LogInScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val fontColor = if (isSystemInDarkTheme()) AppColors.FontPrimaryDark else AppColors.FontPrimaryLight
    val authBg = if(isSystemInDarkTheme()) AppColors.AuthBgDark else AppColors.AuthBg
    val darkTheme = isSystemInDarkTheme()

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
                text = "Log in to Connecto",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = fontColor
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                text = "Welcome back! Sign in using your social account or email to continue us",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = fontColor
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .border(width = 1.dp, shape = CircleShape, color = fontColor)
                        .padding(10.dp)
                        .size(30.dp),
                    painter = painterResource(R.drawable.ic_facebook),
                    contentDescription = "Facebook login!",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    modifier = Modifier
                        .border(width = 1.dp, shape = CircleShape, color = fontColor)
                        .padding(10.dp)
                        .size(30.dp),
                    painter = painterResource(R.drawable.ic_google),
                    contentDescription = "Google login!",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    modifier = Modifier
                        .border(width = 1.dp, shape = CircleShape, color = fontColor)
                        .padding(10.dp)
                        .size(30.dp),
                    painter = painterResource(R.drawable.ic_apple),
                    contentDescription = "apple login!",
                    tint = if (darkTheme) Color.White else Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 20.dp)
            ){
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                    color = Color.Gray
                )
                Text(
                    text = "OR",
                    fontSize = 14.sp,
                    color = fontColor
                )
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    color = Color.Gray
                )
            }
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
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (darkTheme) AppColors.ButtonPrimary else AppColors.ButtonSecondary,
                    contentColor = if (darkTheme) AppColors.FontPrimaryDark else AppColors.FontPrimaryLight
                ),
                onClick = {}
            ) {
                Text(
                    text = "Log In!",
                    fontSize = 15.sp,
                    color = fontColor
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Forgot password?",
                fontSize = 15.sp,
                color = fontColor
            )
        }
    }
}

