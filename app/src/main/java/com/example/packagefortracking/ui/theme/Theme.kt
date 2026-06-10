package com.example.packagefortracking.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = PrimaryLight,
    tertiary = Accent,
    background = AppBackground,
    surface = AppSurface,
    onPrimary = AppSurface,
    onSecondary = AppSurface,
    onTertiary = Primary,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun PackageForTrackingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}