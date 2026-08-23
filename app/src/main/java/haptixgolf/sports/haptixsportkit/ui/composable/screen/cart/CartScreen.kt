package haptixgolf.sports.haptixsportkit.ui.composable.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.RemoveShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import haptixgolf.sports.haptixsportkit.R
import haptixgolf.sports.haptixsportkit.ui.composable.shared.YJIJWContentWrapper
import haptixgolf.sports.haptixsportkit.ui.state.CartItemUiState
import haptixgolf.sports.haptixsportkit.ui.state.DataUiState
import haptixgolf.sports.haptixsportkit.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit
) {
    val state by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val total by viewModel.totalPrice.collectAsStateWithLifecycle()
    YJIJWContentWrapper(
        dataState = state,
        modifier = modifier,
        dataPopulated = {
            CartList(
                items = (state as DataUiState.Populated).data,
                total = total,
                onPlus = viewModel::incrementProductInCart,
                onMinus = viewModel::decrementItemInCart,
                onDelete = viewModel::deleteFromCart,
                onCheckout = onNavigateToCheckoutScreen
            )
        },
        dataEmpty = { CartEmpty() }
    )
}

@Composable
private fun CartList(
    items: List<CartItemUiState>,
    total: Double,
    onPlus: (Int) -> Unit,
    onMinus: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onCheckout: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("YOUR KIT", style = MaterialTheme.typography.headlineMedium)
        Text("Ready when you are.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item { Spacer(Modifier.height(6.dp)) }
            items(items, key = { it.productId }) { item ->
                Card(shape = RoundedCornerShape(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().height(104.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(model = item.productImageUrl, contentDescription = item.productTitle, contentScale = ContentScale.Crop, modifier = Modifier.size(104.dp))
                        Column(modifier = Modifier.weight(1f).padding(10.dp)) {
                            Text(item.productTitle, style = MaterialTheme.typography.titleSmall, maxLines = 2)
                            Text(stringResource(R.string.yjijw_price, item.productPrice), color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Button(onClick = { if (item.quantity == 1) onDelete(item.productId) else onMinus(item.productId) }, modifier = Modifier.size(34.dp)) { Text("−") }
                                Text("  ${item.quantity}  ", fontWeight = FontWeight.Bold)
                                Button(onClick = { onPlus(item.productId) }, modifier = Modifier.size(34.dp)) { Text("+") }
                                Spacer(Modifier.weight(1f))
                                IconButton(onClick = { onDelete(item.productId) }) { Icon(Icons.Rounded.Delete, "Remove item") }
                            }
                        }
                    }
                }
            }
        }
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(16.dp)) {
                Row(Modifier.fillMaxWidth()) {
                    Text("Subtotal")
                    Spacer(Modifier.weight(1f))
                    Text(stringResource(R.string.yjijw_price, total))
                }
                Row(Modifier.fillMaxWidth().padding(top = 6.dp)) {
                    Text("TOTAL", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.weight(1f))
                    Text(stringResource(R.string.yjijw_price, total), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                }
                Button(onClick = onCheckout, modifier = Modifier.fillMaxWidth().padding(top = 14.dp).height(54.dp)) {
                    Text("PROCEED TO CHECKOUT")
                }
            }
        }
    }
}

@Composable
private fun CartEmpty() {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Rounded.RemoveShoppingCart, contentDescription = null, modifier = Modifier.size(72.dp), tint = MaterialTheme.colorScheme.primary)
        Text(stringResource(R.string.yjijw_cart_state_empty_primary_text), style = MaterialTheme.typography.titleLarge)
        Text("Start shopping from the Home tab", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
