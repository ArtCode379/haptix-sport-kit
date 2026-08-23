package haptixgolf.sports.haptixsportkit.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import haptixgolf.sports.haptixsportkit.R
import haptixgolf.sports.haptixsportkit.data.model.Product
import haptixgolf.sports.haptixsportkit.data.model.ProductCategory
import haptixgolf.sports.haptixsportkit.ui.composable.shared.YJIJWContentWrapper
import haptixgolf.sports.haptixsportkit.ui.composable.shared.YJIJWEmptyView
import haptixgolf.sports.haptixsportkit.ui.state.DataUiState
import haptixgolf.sports.haptixsportkit.ui.theme.HaptixGreenDark
import haptixgolf.sports.haptixsportkit.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit
) {
    val productsState by viewModel.productsState.collectAsState()
    HomeContent(
        productsState = productsState,
        modifier = modifier,
        onNavigateToProductDetails = onNavigateToProductDetails,
        onAddProductToCart = viewModel::addToCart
    )
}

@Composable
private fun HomeContent(
    productsState: DataUiState<List<Product>>,
    modifier: Modifier,
    onNavigateToProductDetails: (Int) -> Unit,
    onAddProductToCart: (Int) -> Unit
) {
    YJIJWContentWrapper(
        dataState = productsState,
        modifier = modifier,
        dataPopulated = {
            val products = (productsState as DataUiState.Populated).data
            ProductFeed(products, onNavigateToProductDetails, onAddProductToCart)
        },
        dataEmpty = {
            YJIJWEmptyView(
                primaryText = stringResource(R.string.yjijw_products_state_empty_primary_text),
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}

@Composable
private fun ProductFeed(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Int) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
    var searchVisible by remember { mutableStateOf(false) }
    val visibleProducts = products.filter { selectedCategory == null || it.category == selectedCategory }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(HaptixGreenDark, MaterialTheme.colorScheme.primary)))
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("HAPTIX", style = MaterialTheme.typography.headlineLarge, color = Color.White)
                    Text("GEAR FOR YOUR NEXT MOVE", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.75f))
                }
                IconButton(onClick = { searchVisible = !searchVisible }) {
                    Icon(Icons.Rounded.Search, contentDescription = "Search products", tint = Color.White)
                }
            }
        }
        item {
            val featured = products.first()
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onProductClick(featured.id) }
            ) {
                AsyncImage(
                    model = featured.imageUrl,
                    contentDescription = featured.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.78f))))
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(18.dp)
                ) {
                    Text("PLAY WITH CONTROL", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                    Text(featured.title, style = MaterialTheme.typography.titleLarge, color = Color.White)
                }
                Text(
                    text = stringResource(R.string.yjijw_price, featured.price),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp)
                        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Black
                )
            }
        }
        item {
            Text(
                text = if (searchVisible) "Browse every essential" else "SHOP BY SPORT",
                modifier = Modifier.padding(horizontal = 18.dp),
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    AssistChip(onClick = { selectedCategory = null }, label = { Text("All") })
                }
                items(ProductCategory.entries) { category ->
                    AssistChip(
                        onClick = { selectedCategory = category },
                        label = { Text(stringResource(category.titleRes)) }
                    )
                }
            }
            Text(
                text = "BUILT TO PERFORM",
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        items(visibleProducts, key = { it.id }) { product ->
            ProductRow(product, onProductClick, onAddToCart)
        }
    }
}

@Composable
private fun ProductRow(product: Product, onProductClick: (Int) -> Unit, onAddToCart: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 7.dp)
            .fillMaxWidth()
            .clickable { onProductClick(product.id) },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.height(138.dp)) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            )
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(13.dp)
            ) {
                Text(stringResource(product.category.titleRes).uppercase(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                Text(product.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.yjijw_price, product.price), fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { onAddToCart(product.id) }, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Rounded.AddShoppingCart, contentDescription = "Add ${product.title} to cart", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
