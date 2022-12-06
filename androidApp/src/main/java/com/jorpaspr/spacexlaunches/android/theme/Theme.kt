package com.jorpaspr.spacexlaunches.android.theme

import android.app.Activity
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SpaceXLaunchesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            secondary = Color(0xFF62727b),
        )
    } else {
        lightColors(
            primary = Color(0xff37474f),
            primaryVariant = Color(0xff102027),
            secondary = Color(0xFF62727b),
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )

    val context = LocalContext.current
    LaunchedEffect(darkTheme) {
        (context as? Activity)?.window?.run {
            setNavigationBarTheme(darkTheme)
            statusBarColor = (if (darkTheme) colors.surface else colors.primaryVariant).toArgb()
        }
    }
}

private fun Window.setNavigationBarTheme(darkTheme: Boolean) {
    WindowInsetsControllerCompat(this, decorView)
        .isAppearanceLightNavigationBars = !darkTheme
    navigationBarColor = Color.Transparent.toArgb()
}
