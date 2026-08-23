package haptixgolf.sports.haptixsportkit.ui.composable.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SportsGolf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import haptixgolf.sports.haptixsportkit.R
import haptixgolf.sports.haptixsportkit.ui.theme.HaptixGreenDark
import haptixgolf.sports.haptixsportkit.ui.viewmodel.YJIJWSplashVM
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: YJIJWSplashVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit
) {
    val onboardedState by viewModel.onboardedState.collectAsStateWithLifecycle()
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(900))
        delay(600)
        if (onboardedState) onNavigateToHomeScreen() else onNavigateToOnboarding()
    }
    Box(
        modifier = modifier.fillMaxSize().background(Brush.verticalGradient(listOf(HaptixGreenDark, Color(0xFF071B12)))),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size((110 + progress.value * 50).dp)
                .alpha(1f - progress.value * 0.65f)
                .border(3.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.alpha(progress.value).scale(0.8f + progress.value * 0.2f)) {
            Box(
                modifier = Modifier.size(96.dp).background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.SportsGolf, contentDescription = null, tint = Color.White, modifier = Modifier.size(54.dp))
            }
            Text(
                text = stringResource(R.string.yjijw_app_name).uppercase(),
                modifier = Modifier.padding(top = 22.dp),
                color = Color.White,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
