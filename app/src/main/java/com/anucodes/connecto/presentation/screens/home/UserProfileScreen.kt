package com.anucodes.connecto.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.anucodes.connecto.R
import com.anucodes.connecto.core.authentication.viewmodel.AuthViewModel
import com.anucodes.connecto.ui.theme.AppColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    authViewModel: AuthViewModel
){

    val currentUser by authViewModel.currentUser.collectAsState()

    val isDarkTheme = isSystemInDarkTheme()
    val fontColor = if (isSystemInDarkTheme()) AppColors.FontPrimaryDark else AppColors.FontPrimaryLight

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.DarkBg
                ),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            )
        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = AppColors.DarkBg
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .border(
                            width = 5.dp,
                            color = AppColors.Primary,
                            shape = CircleShape
                        )
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(currentUser?.profilePictureUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.profile_placeholder),
                        fallback = painterResource(R.drawable.profile_placeholder),
                        placeholder = painterResource(R.drawable.profile_placeholder)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                currentUser?.username?.trim('"')?.let {
                    Text(
                        text = "@${it}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (isDarkTheme) AppColors.FontHintDark else AppColors.FontHintLight
                    )
                }
            }

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(
                        color = if (isDarkTheme) AppColors.DarkCard else AppColors.LightCard
                    )
                    .fillMaxWidth()
                    .weight(7f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.weight(8f))

                currentUser?.name?.let {
                    UserInfoTile(
                        title = "Display name",
                        value = it
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                currentUser?.email?.let {
                    UserInfoTile(
                        title = "Email",
                        value = it
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                currentUser?.username?.let {
                    UserInfoTile(
                        title = "Username",
                        value = it
                    )
                }

                Spacer(modifier = Modifier.weight(3f))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkTheme) AppColors.ButtonPrimary else AppColors.ButtonSecondary,
                        contentColor = if (isDarkTheme) AppColors.FontPrimaryDark else AppColors.FontPrimaryLight
                    ),
                    onClick = {
                        authViewModel.signOutCurrentUser()
                        navController.navigate("auth_graph"){
                            popUpTo(0){inclusive=true}
                        }
                    }
                ) {
                    Text(
                        text = "Log Out!",
                        fontSize = 15.sp,
                        color = fontColor
                    )
                }
                
                Spacer(modifier = Modifier.weight(10f))
            }
        }
    }
}


@Composable
fun UserInfoTile(
    title: String,
    value: String
) {

    val isDarkTheme = isSystemInDarkTheme()


    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 1.dp,
                color = if (isDarkTheme) AppColors.DarkInputBorder else AppColors.LightInputBorder,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                color = if (isDarkTheme) AppColors.DarkBg else AppColors.LightBg
            )
            .padding(15.dp)
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            color = if (isDarkTheme) AppColors.FontHintDark else AppColors.FontHintLight
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = if (isDarkTheme) AppColors.FontPrimaryDark else AppColors.FontPrimaryLight
        )
    }
}