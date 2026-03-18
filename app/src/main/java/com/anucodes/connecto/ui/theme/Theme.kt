package com.anucodes.connecto.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val LightColorScheme = lightColorScheme(
    primary              = AppColors.Primary,
    onPrimary            = AppColors.ButtonPrimaryText,
    primaryContainer     = AppColors.PrimaryContainer,
    onPrimaryContainer   = AppColors.FontPrimaryLight,

    secondary            = AppColors.PrimaryVariant,
    onSecondary          = AppColors.ButtonPrimaryText,
    secondaryContainer   = AppColors.LightSurface,
    onSecondaryContainer = AppColors.FontSecondaryLight,

    background           = AppColors.LightBg,
    onBackground         = AppColors.FontPrimaryLight,

    surface              = AppColors.LightCard,
    onSurface            = AppColors.FontPrimaryLight,

    surfaceVariant       = AppColors.LightSurface,
    onSurfaceVariant     = AppColors.FontSecondaryLight,

    error                = AppColors.Error,
    onError              = AppColors.OnError,

    outline              = AppColors.LightInputBorder,
    outlineVariant       = AppColors.LightDivider
)

private val DarkColorScheme = darkColorScheme(
    primary              = AppColors.PrimaryVariant,
    onPrimary            = AppColors.ButtonPrimaryText,
    primaryContainer     = AppColors.PrimaryContainerDark,
    onPrimaryContainer   = AppColors.FontPrimaryDark,

    secondary            = AppColors.PrimaryLight,
    onSecondary          = AppColors.DarkBg,
    secondaryContainer   = AppColors.DarkSurface,
    onSecondaryContainer = AppColors.FontSecondaryDark,

    background           = AppColors.DarkBg,
    onBackground         = AppColors.FontPrimaryDark,

    surface              = AppColors.DarkCard,
    onSurface            = AppColors.FontPrimaryDark,

    surfaceVariant       = AppColors.DarkSurface,
    onSurfaceVariant     = AppColors.FontSecondaryDark,

    error                = Color(0xFFFCA5A5),
    onError              = Color(0xFF7F1D1D),

    outline              = AppColors.DarkInputBorder,
    outlineVariant       = AppColors.DarkDivider
)

@Composable
fun ConnectoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}