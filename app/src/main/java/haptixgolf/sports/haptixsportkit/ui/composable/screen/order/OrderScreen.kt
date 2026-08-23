package haptixgolf.sports.haptixsportkit.ui.composable.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import haptixgolf.sports.haptixsportkit.R
import haptixgolf.sports.haptixsportkit.data.entity.OrderEntity
import haptixgolf.sports.haptixsportkit.ui.composable.shared.YJIJWContentWrapper
import haptixgolf.sports.haptixsportkit.ui.state.DataUiState
import haptixgolf.sports.haptixsportkit.ui.viewmodel.OrderViewModel
import java.time.format.DateTimeFormatter
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(modifier: Modifier = Modifier, viewModel: OrderViewModel = koinViewModel()) {
    val state by viewModel.ordersState.collectAsState()
    YJIJWContentWrapper(
        dataState = state,
        modifier = modifier,
        dataPopulated = { OrderList((state as DataUiState.Populated).data.sortedByDescending { it.timestamp }) },
        dataEmpty = { OrderEmpty() }
    )
}

@Composable
private fun OrderList(orders: List<OrderEntity>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("ORDER HISTORY", style = MaterialTheme.typography.headlineMedium)
            Text("Your reserved equipment in one place", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        items(orders, key = { it.orderNumber }) { order ->
            Card(shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("#${order.orderNumber}", fontWeight = FontWeight.Black)
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "COMPLETED",
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(20.dp))
                                .padding(8.dp),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    Text(order.timestamp.format(DateTimeFormatter.ofPattern("dd MMM yyyy • HH:mm")), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(order.description, style = MaterialTheme.typography.bodyMedium)
                    Text(stringResource(R.string.yjijw_price, order.price), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

@Composable
private fun OrderEmpty() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(Icons.Rounded.ReceiptLong, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(stringResource(R.string.yjijw_orders_state_empty_primary_text), style = MaterialTheme.typography.titleLarge)
        Text("Reserved orders will appear here", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
