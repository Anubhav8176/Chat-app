package com.anucodes.connecto.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.anucodes.connecto.R
import com.anucodes.connecto.core.authentication.viewmodel.AuthViewModel
import com.anucodes.connecto.ui.theme.AppColors


@Composable
fun HomeScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {

    val fontColor = if (isSystemInDarkTheme()) AppColors.FontPrimaryDark else AppColors.FontPrimaryLight
    val currentUser by authViewModel.currentUser.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()

    Scaffold(
        topBar = { TopHomeBar(
            imageUrl = currentUser?.profilePictureUrl
        ) }
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
                    .weight(1.25f)
            ) {
                LazyRow(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
                ) {
                    items(20){
                        FriendComposable(
                            imageUrl = currentUser?.profilePictureUrl,
                            username = currentUser?.name?.split(" ")?.first()
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(
                        color = if (isDarkTheme) AppColors.DarkCard else AppColors.LightCard
                    )
                    .fillMaxWidth()
                    .weight(8f)
            ) {
                LazyColumn {
                    items(20){
                        ChatTile(
                            fontColor = fontColor,
                            imageUrl = "",
                            name = "Anubhav Singh"
                        )
                    }
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHomeBar(
    imageUrl: String? = ""
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppColors.DarkBg
        ),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Home",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        color = AppColors.DarkCard
                    ),
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = AppColors.PrimaryLight
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(42.dp)
                    .border(
                        width = 2.dp,
                        color = AppColors.Primary,
                        shape = CircleShape
                    )
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.profile_placeholder),
                    fallback = painterResource(R.drawable.profile_placeholder),
                    placeholder = painterResource(R.drawable.profile_placeholder)
                )
            }
        }
    )
}


@Composable
fun FriendComposable(
    imageUrl: String?,
    username: String?
){
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(65.dp)
                .border(
                    width = 2.dp,
                    color = AppColors.Primary,
                    shape = CircleShape
                )
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.profile_placeholder),
                fallback = painterResource(R.drawable.profile_placeholder),
                placeholder = painterResource(R.drawable.profile_placeholder)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (username != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = username.trim('"'),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}


@Composable
fun ChatTile(
    fontColor: Color,
    imageUrl: String?,
    name: String
){
    Row(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .border(
                    width = 2.dp,
                    color = AppColors.Primary,
                    shape = CircleShape
                )
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.profile_placeholder),
                fallback = painterResource(R.drawable.profile_placeholder),
                placeholder = painterResource(R.drawable.profile_placeholder)
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = name.trim('"'),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = fontColor
            )
            Text(
                text = "Don't get late be on time and bring all the necessary items along with you as well.",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = fontColor
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "2 min ago",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = fontColor
            )

            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(color = AppColors.Error),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    lineHeight = 9.sp
                )
            }
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.88f),
            color = if (isSystemInDarkTheme()) AppColors.LightDivider else AppColors.DarkDivider
        )
    }
}
