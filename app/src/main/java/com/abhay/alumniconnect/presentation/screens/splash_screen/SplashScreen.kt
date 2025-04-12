package com.abhay.alumniconnect.presentation.screens.splash_screen

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn != null) {
            delay(2000)
            onTimeout(isLoggedIn)
        }
    }
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Scaling icon to 1.2 then back to 1
        scale.animateTo(
            targetValue = 1.2f, animationSpec = tween(
                durationMillis = 400, easing = FastOutSlowInEasing
            )
        )
        scale.animateTo(
            targetValue = 1f, animationSpec = tween(
                durationMillis = 200, easing = LinearOutSlowInEasing
            )
        )

        // Text fade in
        alpha.animateTo(
            targetValue = 1f, animationSpec = tween(
                durationMillis = 300, delayMillis = 200
            )
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    })

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(alpha.value)
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