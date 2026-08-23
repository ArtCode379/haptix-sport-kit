package haptixgolf.sports.haptixsportkit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HaptixColorScheme = lightColorScheme(
    primary = HaptixGreen,
    onPrimary = Color.White,
    primaryContainer = HaptixChip,
    onPrimaryContainer = HaptixGreenDark,
    secondary = HaptixOrange,
    onSecondary = Color.White,
    background = HaptixBackground,
    onBackground = HaptixInk,
    surface = HaptixSurface,
    onSurface = HaptixInk,
    surfaceVariant = HaptixChip,
    onSurfaceVariant = HaptixMuted,
    outline = HaptixBorder,
    tertiary = HaptixSuccess,
    error = Color(0xFFBA1A1A)
)

@Composable
fun ProductAppYJIJWTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HaptixColorScheme,
        typography = Typography,
        content = content
    )
}
