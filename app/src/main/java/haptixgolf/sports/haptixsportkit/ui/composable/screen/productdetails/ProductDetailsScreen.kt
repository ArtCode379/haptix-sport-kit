package haptixgolf.sports.haptixsportkit.ui.composable.screen.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import haptixgolf.sports.haptixsportkit.R
import haptixgolf.sports.haptixsportkit.data.model.Product
import haptixgolf.sports.haptixsportkit.ui.composable.shared.YJIJWContentWrapper
import haptixgolf.sports.haptixsportkit.ui.composable.shared.YJIJWEmptyView
import haptixgolf.sports.haptixsportkit.ui.state.DataUiState
import haptixgolf.sports.haptixsportkit.ui.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel()
) {
    val productState by viewModel.productDetailsState.collectAsState()
    LaunchedEffect(productId) { viewModel.observeProductDetails(productId) }
    ProductDetailsScreenContent(productState, modifier, viewModel::addProductToCart)
}

@Composable
private fun ProductDetailsScreenContent(
    productState: DataUiState<Product>,
    modifier: Modifier,
    onAddToCart: () -> Unit
) {
    var cartAdded by remember { mutableStateOf(false) }
    LaunchedEffect(cartAdded) {
        if (cartAdded) {
            delay(2000)
            cartAdded = false
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        YJIJWContentWrapper(
            dataState = productState,
            dataPopulated = {
                ProductDetails((productState as DataUiState.Populated).data) {
                    onAddToCart()
                    cartAdded = true
                }
            },
            dataEmpty = {
                YJIJWEmptyView(
                    modifier = Modifier.fillMaxSize(),
                    primaryText = stringResource(R.string.yjijw_product_details_state_empty_primary_text)
                )
            }
        )
        AnimatedVisibility(
            visible = cartAdded,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically { it },
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color.White)
                Text("  ADDED TO CART", color = Color.White, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun ProductDetails(product: Product, onAddToCart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 88.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(310.dp)) {
            AsyncImage(model = product.imageUrl, contentDescription = product.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.72f)))))
            Text(
                text = stringResource(R.string.yjijw_price, product.price),
                modifier = Modifier.align(Alignment.BottomStart).padding(20.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(stringResource(product.category.titleRes).uppercase(), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
            Text(product.title, style = MaterialTheme.typography.headlineMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SpecChip(Icons.Rounded.Shield, "Quality tested")
                SpecChip(Icons.Rounded.LocalShipping, "Reserve today")
            }
            Text(product.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Button(
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(stringResource(R.string.yjijw_button_add_to_cart_label).uppercase(), fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun SpecChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Row(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primaryContainer).padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text("  $label", style = MaterialTheme.typography.labelMedium)
    }
}
