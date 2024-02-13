package dev.falkow.blanco.layout.display.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import dev.falkow.blanco.shared.config.ColorSchemes
import dev.falkow.blanco.shared.config.ColorThemes

@Composable
fun AppTheme(
    preferredColorScheme: ColorSchemes,
    preferredColorTheme: ColorThemes,
    preferredDynamicColor: Boolean, // Dynamic color is available on Android 12+
    content: @Composable () -> Unit,
) {

    fun getAppColorTheme(appColorSet: AppColorSet): Pair<ColorScheme, ColorScheme> =
        Pair(
            lightColorScheme(
                primary = appColorSet.md_theme_light_primary,
                onPrimary = appColorSet.md_theme_light_onPrimary,
                primaryContainer = appColorSet.md_theme_light_primaryContainer,
                onPrimaryContainer = appColorSet.md_theme_light_onPrimaryContainer,
                secondary = appColorSet.md_theme_light_secondary,
                onSecondary = appColorSet.md_theme_light_onSecondary,
                secondaryContainer = appColorSet.md_theme_light_secondaryContainer,
                onSecondaryContainer = appColorSet.md_theme_light_onSecondaryContainer,
                tertiary = appColorSet.md_theme_light_tertiary,
                onTertiary = appColorSet.md_theme_light_onTertiary,
                tertiaryContainer = appColorSet.md_theme_light_tertiaryContainer,
                onTertiaryContainer = appColorSet.md_theme_light_onTertiaryContainer,
                error = appColorSet.md_theme_light_error,
                errorContainer = appColorSet.md_theme_light_errorContainer,
                onError = appColorSet.md_theme_light_onError,
                onErrorContainer = appColorSet.md_theme_light_onErrorContainer,
                background = appColorSet.md_theme_light_background,
                onBackground = appColorSet.md_theme_light_onBackground,
                surface = appColorSet.md_theme_light_surface,
                onSurface = appColorSet.md_theme_light_onSurface,
                surfaceVariant = appColorSet.md_theme_light_surfaceVariant,
                onSurfaceVariant = appColorSet.md_theme_light_onSurfaceVariant,
                outline = appColorSet.md_theme_light_outline,
                inverseOnSurface = appColorSet.md_theme_light_inverseOnSurface,
                inverseSurface = appColorSet.md_theme_light_inverseSurface,
                inversePrimary = appColorSet.md_theme_light_inversePrimary,
                surfaceTint = appColorSet.md_theme_light_surfaceTint,
            ),
            darkColorScheme(
                primary = appColorSet.md_theme_dark_primary,
                onPrimary = appColorSet.md_theme_dark_onPrimary,
                primaryContainer = appColorSet.md_theme_dark_primaryContainer,
                onPrimaryContainer = appColorSet.md_theme_dark_onPrimaryContainer,
                secondary = appColorSet.md_theme_dark_secondary,
                onSecondary = appColorSet.md_theme_dark_onSecondary,
                secondaryContainer = appColorSet.md_theme_dark_secondaryContainer,
                onSecondaryContainer = appColorSet.md_theme_dark_onSecondaryContainer,
                tertiary = appColorSet.md_theme_dark_tertiary,
                onTertiary = appColorSet.md_theme_dark_onTertiary,
                tertiaryContainer = appColorSet.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = appColorSet.md_theme_dark_onTertiaryContainer,
                error = appColorSet.md_theme_dark_error,
                errorContainer = appColorSet.md_theme_dark_errorContainer,
                onError = appColorSet.md_theme_dark_onError,
                onErrorContainer = appColorSet.md_theme_dark_onErrorContainer,
                background = appColorSet.md_theme_dark_background,
                onBackground = appColorSet.md_theme_dark_onBackground,
                surface = appColorSet.md_theme_dark_surface,
                onSurface = appColorSet.md_theme_dark_onSurface,
                surfaceVariant = appColorSet.md_theme_dark_surfaceVariant,
                onSurfaceVariant = appColorSet.md_theme_dark_onSurfaceVariant,
                outline = appColorSet.md_theme_dark_outline,
                inverseOnSurface = appColorSet.md_theme_dark_inverseOnSurface,
                inverseSurface = appColorSet.md_theme_dark_inverseSurface,
                inversePrimary = appColorSet.md_theme_dark_inversePrimary,
                surfaceTint = appColorSet.md_theme_dark_surfaceTint,
            )
        )

    val colorTheme: Pair<ColorScheme, ColorScheme> =
        when (preferredColorTheme) {
            ColorThemes.BLUE -> getAppColorTheme(colorSetBlue)
            ColorThemes.GREEN -> getAppColorTheme(colorSetGreen)
            ColorThemes.INDIGO -> getAppColorTheme(colorSetIndigo)
            ColorThemes.SAND -> getAppColorTheme(colorSetSand)
            ColorThemes.TEAL -> getAppColorTheme(colorSetTeal)
        }

    val darkTheme = preferredColorScheme == ColorSchemes.DARK
            || (preferredColorScheme == ColorSchemes.AUTO && isSystemInDarkTheme())

    val colorScheme = when {
        preferredDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> colorTheme.second // DarkColorScheme
        else -> colorTheme.first // LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
                ?: throw Exception("Not in an activity - unable to get Window reference")
            // window.statusBarColor = if(colorScheme == DarkColorScheme) colorScheme.primaryContainer.toArgb() else colorScheme.surfaceTint.toArgb()
            // window.statusBarColor = colorScheme.inversePrimary.toArgb()
            // window.navigationBarColor = colorScheme.inversePrimary.toArgb()
            // window.statusBarColor = colorScheme.surfaceColorAtElevation(12.dp).toArgb()
            // window.navigationBarColor = colorScheme.surfaceColorAtElevation(12.dp).toArgb()
            window.statusBarColor = colorScheme.surfaceColorAtElevation(3.dp).toArgb()
            window.navigationBarColor = colorScheme.surfaceColorAtElevation(3.dp).toArgb()
            // Level 0: 0dp
            // Level 1: 1dp
            // Level 2: 3dp
            // Level 3: 6dp
            // Level 4: 8dp
            // Level 5: 12dp
            // window.statusBarColor = colorScheme.surfaceVariant.toArgb()
            // window.navigationBarColor = colorScheme.surfaceVariant.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !darkTheme

            // window.navigationBarDividerColor = colorScheme.primaryContainer.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typographyTheme,
        content = content,
    )
}