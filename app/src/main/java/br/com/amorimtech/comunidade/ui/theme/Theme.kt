package br.com.amorimtech.comunidade.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = NavyDark,
    onPrimary = Color.White,
    primaryContainer = NavyLight,
    onPrimaryContainer = IvoryBackground,
    secondary = Copper,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFBECE2),
    onSecondaryContainer = CopperDark,
    tertiary = Amber,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFDF4DE),
    onTertiaryContainer = AmberMuted,
    background = IvoryBackground,
    onBackground = TextPrimary,
    surface = IvorySurface,
    onSurface = TextPrimary,
    surfaceVariant = IvorySurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = IvoryBorder,
    outlineVariant = Color(0xFFDFD7C7)
)

val AmorimShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun ComunidadeTheme(
    darkTheme: Boolean = false, // Design system da AmorimTech é baseado no fundo Ivory e Navy
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = NavyDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AmorimShapes,
        content = content
    )
}
