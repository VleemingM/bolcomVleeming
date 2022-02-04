package nl.vleeming.bolcomvleeming.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = BolBlue,
    primaryVariant = BolBlueLighter,
    background = Color.Black,
    onBackground = Color.White
)

private val LightColorPalette = lightColors(
    primary = BolBlue,
    primaryVariant = BolBlueLight,
    background = Color.White,
    onBackground = Color.Black,


    /* Other default colors to override
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    */
)

@get:Composable
val Colors.shoppingCart: Color
    get() = ShoppingCartYellowDark

@get:Composable
val Colors.onShoppingCart: Color
    get() = Color.Black

@Composable
fun BolComVleemingTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}