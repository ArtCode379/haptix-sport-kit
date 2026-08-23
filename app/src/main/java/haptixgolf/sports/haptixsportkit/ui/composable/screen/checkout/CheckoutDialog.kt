package haptixgolf.sports.haptixsportkit.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import haptixgolf.sports.haptixsportkit.R

@Composable
fun CheckoutDialog(orderNumber: String, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onConfirm,
        confirmButton = { TextButton(onClick = onConfirm) { Text(stringResource(R.string.yjijw_checkout_dialog_confirm)) } },
        title = { Text(stringResource(R.string.yjijw_checkout_dialog_title), fontWeight = FontWeight.Bold) },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Text("ORDER #$orderNumber", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Black)
                Text(stringResource(R.string.yjijw_checkout_success_message), modifier = Modifier.padding(top = 10.dp))
            }
        }
    )
}
