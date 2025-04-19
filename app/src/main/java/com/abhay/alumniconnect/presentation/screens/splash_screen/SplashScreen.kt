package com.abhay.alumniconnect.presentation.screens.splash_screen

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhay.alumniconnect.R
import com.example.compose.AlumniConnectTheme
import com.example.ui.theme.someFontFamily
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: (Boolean) -> Unit
) {
    val viewModel = hiltViewModel<SplashViewModel>()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value

    val splitAnimation = remember { Animatable(0f) }
    val contentAlpha = remember { Animatable(0f) }
    val iconScale = remember { Animatable(0f) }

    // Split animation progress (0f to 1f)
    // 0f = screens closed (centered), 1f = screens fully open
    val splitProgress by splitAnimation.asState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn != null) {
            delay(2000)
            onTimeout(isLoggedIn)
        }
    }

    LaunchedEffect(Unit) {
        // First phase: split the screen
        splitAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )

        // Second phase: Show content
        iconScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        )

        // Third phase: Fade in text
        contentAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 300,
                delayMillis = 100
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Split screen animation
        Box(modifier = Modifier.fillMaxSize()) {
            val primaryColor = MaterialTheme.colorScheme.primary
            Canvas(modifier = Modifier.fillMaxSize()) {

                // Left half (moves left)
                drawRect(
                    color = primaryColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width / 2 * (1f - splitProgress), size.height)
                )

                // Right half (moves right)
                drawRect(
                    color = primaryColor,
                    topLeft = Offset(size.width - (size.width / 2 * (1f - splitProgress)), 0f),
                    size = Size(size.width / 2 * (1f - splitProgress), size.height)
                )
            }
        }

        // Content (visible once split animation completes)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo appears and scales in
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        scaleX = iconScale.value
                        scaleY = iconScale.value
                    }
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Text fades in
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(contentAlpha.value)
            ) {
                Text(
                    text = "Alumni",
                    fontSize = 64.sp,
                    style = MaterialTheme.typography.displayLarge,
                    fontFamily = someFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Connect",
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = someFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SplashScreenPreview() {
    AlumniConnectTheme {
        SplashScreen() {}
    }
}