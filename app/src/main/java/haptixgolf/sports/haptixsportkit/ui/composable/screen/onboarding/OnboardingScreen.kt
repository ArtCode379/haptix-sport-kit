package haptixgolf.sports.haptixsportkit.ui.composable.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import haptixgolf.sports.haptixsportkit.R
import haptixgolf.sports.haptixsportkit.ui.viewmodel.YJIJWOnboardingVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

data class OnboardingContent(
    @field:StringRes val titleRes: Int,
    @field:StringRes val descriptionRes: Int,
    @field:DrawableRes val imageRes: Int
)

private val onboardingPagesContent = listOf(
    OnboardingContent(R.string.yjijw_page_1_title, R.string.yjijw_page_1_description, R.drawable.onboarding_golf),
    OnboardingContent(R.string.yjijw_page_2_title, R.string.yjijw_page_2_description, R.drawable.onboarding_fitness),
    OnboardingContent(R.string.yjijw_page_3_title, R.string.yjijw_page_3_description, R.drawable.onboarding_racket)
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: YJIJWOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {
    val onboardingSetState by viewModel.onboardingSetState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    LaunchedEffect(onboardingSetState) {
        if (onboardingSetState) onNavigateToHomeScreen()
    }
    Box(modifier = modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            val content = onboardingPagesContent[page]
            Box(modifier = Modifier.fillMaxSize()) {
                Image(painterResource(content.imageRes), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Color.Black.copy(alpha = 0.9f)))))
                Column(
                    modifier = Modifier.align(Alignment.BottomStart).padding(horizontal = 24.dp, vertical = 108.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(stringResource(content.titleRes), color = Color.White, style = MaterialTheme.typography.displaySmall)
                    Text(stringResource(content.descriptionRes), color = Color.White.copy(alpha = 0.82f), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        TextButton(onClick = viewModel::setOnboarded, modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)) {
            Text(stringResource(R.string.yjijw_skip_button_title), color = Color.White)
        }
        Row(
            modifier = Modifier.align(Alignment.BottomStart).padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier.size(if (pagerState.currentPage == index) 24.dp else 8.dp, 8.dp)
                        .background(if (pagerState.currentPage == index) MaterialTheme.colorScheme.secondary else Color.White.copy(alpha = 0.55f), CircleShape)
                )
            }
        }
        Button(
            onClick = {
                if (pagerState.currentPage == 2) viewModel.setOnboarded()
                else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(18.dp)
        ) {
            Text(if (pagerState.currentPage == 2) stringResource(R.string.yjijw_start_button_title) else stringResource(R.string.yjijw_next_button_title), fontWeight = FontWeight.Bold)
        }
    }
}
