package com.example.compose
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.abhay.alumniconnect.ui.theme.backgroundDark
import com.abhay.alumniconnect.ui.theme.backgroundDarkHighContrast
import com.abhay.alumniconnect.ui.theme.backgroundDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.backgroundLight
import com.abhay.alumniconnect.ui.theme.backgroundLightHighContrast
import com.abhay.alumniconnect.ui.theme.backgroundLightMediumContrast
import com.abhay.alumniconnect.ui.theme.errorContainerDark
import com.abhay.alumniconnect.ui.theme.errorContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.errorContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.errorContainerLight
import com.abhay.alumniconnect.ui.theme.errorContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.errorContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.errorDark
import com.abhay.alumniconnect.ui.theme.errorDarkHighContrast
import com.abhay.alumniconnect.ui.theme.errorDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.errorLight
import com.abhay.alumniconnect.ui.theme.errorLightHighContrast
import com.abhay.alumniconnect.ui.theme.errorLightMediumContrast
import com.abhay.alumniconnect.ui.theme.inverseOnSurfaceDark
import com.abhay.alumniconnect.ui.theme.inverseOnSurfaceDarkHighContrast
import com.abhay.alumniconnect.ui.theme.inverseOnSurfaceDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.inverseOnSurfaceLight
import com.abhay.alumniconnect.ui.theme.inverseOnSurfaceLightHighContrast
import com.abhay.alumniconnect.ui.theme.inverseOnSurfaceLightMediumContrast
import com.abhay.alumniconnect.ui.theme.inversePrimaryDark
import com.abhay.alumniconnect.ui.theme.inversePrimaryDarkHighContrast
import com.abhay.alumniconnect.ui.theme.inversePrimaryDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.inversePrimaryLight
import com.abhay.alumniconnect.ui.theme.inversePrimaryLightHighContrast
import com.abhay.alumniconnect.ui.theme.inversePrimaryLightMediumContrast
import com.abhay.alumniconnect.ui.theme.inverseSurfaceDark
import com.abhay.alumniconnect.ui.theme.inverseSurfaceDarkHighContrast
import com.abhay.alumniconnect.ui.theme.inverseSurfaceDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.inverseSurfaceLight
import com.abhay.alumniconnect.ui.theme.inverseSurfaceLightHighContrast
import com.abhay.alumniconnect.ui.theme.inverseSurfaceLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onBackgroundDark
import com.abhay.alumniconnect.ui.theme.onBackgroundDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onBackgroundDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onBackgroundLight
import com.abhay.alumniconnect.ui.theme.onBackgroundLightHighContrast
import com.abhay.alumniconnect.ui.theme.onBackgroundLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onErrorContainerDark
import com.abhay.alumniconnect.ui.theme.onErrorContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onErrorContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onErrorContainerLight
import com.abhay.alumniconnect.ui.theme.onErrorContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.onErrorContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onErrorDark
import com.abhay.alumniconnect.ui.theme.onErrorDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onErrorDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onErrorLight
import com.abhay.alumniconnect.ui.theme.onErrorLightHighContrast
import com.abhay.alumniconnect.ui.theme.onErrorLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onPrimaryContainerDark
import com.abhay.alumniconnect.ui.theme.onPrimaryContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onPrimaryContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onPrimaryContainerLight
import com.abhay.alumniconnect.ui.theme.onPrimaryContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.onPrimaryContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onPrimaryDark
import com.abhay.alumniconnect.ui.theme.onPrimaryDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onPrimaryDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onPrimaryLight
import com.abhay.alumniconnect.ui.theme.onPrimaryLightHighContrast
import com.abhay.alumniconnect.ui.theme.onPrimaryLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onSecondaryContainerDark
import com.abhay.alumniconnect.ui.theme.onSecondaryContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onSecondaryContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onSecondaryContainerLight
import com.abhay.alumniconnect.ui.theme.onSecondaryContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.onSecondaryContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onSecondaryDark
import com.abhay.alumniconnect.ui.theme.onSecondaryDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onSecondaryDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onSecondaryLight
import com.abhay.alumniconnect.ui.theme.onSecondaryLightHighContrast
import com.abhay.alumniconnect.ui.theme.onSecondaryLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onSurfaceDark
import com.abhay.alumniconnect.ui.theme.onSurfaceDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onSurfaceDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onSurfaceLight
import com.abhay.alumniconnect.ui.theme.onSurfaceLightHighContrast
import com.abhay.alumniconnect.ui.theme.onSurfaceLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onSurfaceVariantDark
import com.abhay.alumniconnect.ui.theme.onSurfaceVariantDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onSurfaceVariantDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onSurfaceVariantLight
import com.abhay.alumniconnect.ui.theme.onSurfaceVariantLightHighContrast
import com.abhay.alumniconnect.ui.theme.onSurfaceVariantLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onTertiaryContainerDark
import com.abhay.alumniconnect.ui.theme.onTertiaryContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onTertiaryContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onTertiaryContainerLight
import com.abhay.alumniconnect.ui.theme.onTertiaryContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.onTertiaryContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.onTertiaryDark
import com.abhay.alumniconnect.ui.theme.onTertiaryDarkHighContrast
import com.abhay.alumniconnect.ui.theme.onTertiaryDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.onTertiaryLight
import com.abhay.alumniconnect.ui.theme.onTertiaryLightHighContrast
import com.abhay.alumniconnect.ui.theme.onTertiaryLightMediumContrast
import com.abhay.alumniconnect.ui.theme.outlineDark
import com.abhay.alumniconnect.ui.theme.outlineDarkHighContrast
import com.abhay.alumniconnect.ui.theme.outlineDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.outlineLight
import com.abhay.alumniconnect.ui.theme.outlineLightHighContrast
import com.abhay.alumniconnect.ui.theme.outlineLightMediumContrast
import com.abhay.alumniconnect.ui.theme.outlineVariantDark
import com.abhay.alumniconnect.ui.theme.outlineVariantDarkHighContrast
import com.abhay.alumniconnect.ui.theme.outlineVariantDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.outlineVariantLight
import com.abhay.alumniconnect.ui.theme.outlineVariantLightHighContrast
import com.abhay.alumniconnect.ui.theme.outlineVariantLightMediumContrast
import com.abhay.alumniconnect.ui.theme.primaryContainerDark
import com.abhay.alumniconnect.ui.theme.primaryContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.primaryContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.primaryContainerLight
import com.abhay.alumniconnect.ui.theme.primaryContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.primaryContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.primaryDark
import com.abhay.alumniconnect.ui.theme.primaryDarkHighContrast
import com.abhay.alumniconnect.ui.theme.primaryDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.primaryLight
import com.abhay.alumniconnect.ui.theme.primaryLightHighContrast
import com.abhay.alumniconnect.ui.theme.primaryLightMediumContrast
import com.abhay.alumniconnect.ui.theme.scrimDark
import com.abhay.alumniconnect.ui.theme.scrimDarkHighContrast
import com.abhay.alumniconnect.ui.theme.scrimDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.scrimLight
import com.abhay.alumniconnect.ui.theme.scrimLightHighContrast
import com.abhay.alumniconnect.ui.theme.scrimLightMediumContrast
import com.abhay.alumniconnect.ui.theme.secondaryContainerDark
import com.abhay.alumniconnect.ui.theme.secondaryContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.secondaryContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.secondaryContainerLight
import com.abhay.alumniconnect.ui.theme.secondaryContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.secondaryContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.secondaryDark
import com.abhay.alumniconnect.ui.theme.secondaryDarkHighContrast
import com.abhay.alumniconnect.ui.theme.secondaryDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.secondaryLight
import com.abhay.alumniconnect.ui.theme.secondaryLightHighContrast
import com.abhay.alumniconnect.ui.theme.secondaryLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceBrightDark
import com.abhay.alumniconnect.ui.theme.surfaceBrightDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceBrightDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceBrightLight
import com.abhay.alumniconnect.ui.theme.surfaceBrightLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceBrightLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerDark
import com.abhay.alumniconnect.ui.theme.surfaceContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighDark
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighLight
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighestDark
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighestDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighestDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighestLight
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighestLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerHighestLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLight
import com.abhay.alumniconnect.ui.theme.surfaceContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowDark
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowLight
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowestDark
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowestDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowestDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowestLight
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowestLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceContainerLowestLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceDark
import com.abhay.alumniconnect.ui.theme.surfaceDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceDimDark
import com.abhay.alumniconnect.ui.theme.surfaceDimDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceDimDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceDimLight
import com.abhay.alumniconnect.ui.theme.surfaceDimLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceDimLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceLight
import com.abhay.alumniconnect.ui.theme.surfaceLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceLightMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceVariantDark
import com.abhay.alumniconnect.ui.theme.surfaceVariantDarkHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceVariantDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.surfaceVariantLight
import com.abhay.alumniconnect.ui.theme.surfaceVariantLightHighContrast
import com.abhay.alumniconnect.ui.theme.surfaceVariantLightMediumContrast
import com.abhay.alumniconnect.ui.theme.tertiaryContainerDark
import com.abhay.alumniconnect.ui.theme.tertiaryContainerDarkHighContrast
import com.abhay.alumniconnect.ui.theme.tertiaryContainerDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.tertiaryContainerLight
import com.abhay.alumniconnect.ui.theme.tertiaryContainerLightHighContrast
import com.abhay.alumniconnect.ui.theme.tertiaryContainerLightMediumContrast
import com.abhay.alumniconnect.ui.theme.tertiaryDark
import com.abhay.alumniconnect.ui.theme.tertiaryDarkHighContrast
import com.abhay.alumniconnect.ui.theme.tertiaryDarkMediumContrast
import com.abhay.alumniconnect.ui.theme.tertiaryLight
import com.abhay.alumniconnect.ui.theme.tertiaryLightHighContrast
import com.abhay.alumniconnect.ui.theme.tertiaryLightMediumContrast
import com.example.ui.theme.AppShapes
import com.example.ui.theme.AppTypography

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AlumniConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content,
    shapes = AppShapes
  )
}

