package haptixgolf.sports.haptixsportkit.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import haptixgolf.sports.haptixsportkit.R
import haptixgolf.sports.haptixsportkit.data.entity.OrderEntity
import haptixgolf.sports.haptixsportkit.ui.state.DataUiState
import haptixgolf.sports.haptixsportkit.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalid by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    val enabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotBlank() && viewModel.customerLastName.isNotBlank() && viewModel.customerEmail.isNotBlank()
        }
    }
    if (orderState is DataUiState.Populated) {
        CheckoutDialog((orderState as DataUiState.Populated<OrderEntity>).data.orderNumber, onNavigateToOrdersScreen)
    }
    CheckoutContent(
        viewModel.customerFirstName,
        viewModel.customerLastName,
        viewModel.customerEmail,
        emailInvalid,
        modifier,
        focusManager,
        enabled,
        viewModel::updateCustomerFirstName,
        viewModel::updateCustomerLastName,
        viewModel::updateCustomerEmail,
        viewModel::placeOrder
    )
}

@Composable
private fun CheckoutContent(
    firstName: String,
    lastName: String,
    email: String,
    isEmailInvalid: Boolean,
    modifier: Modifier,
    focusManager: FocusManager,
    enabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPlaceOrder: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("RESERVE YOUR KIT", style = MaterialTheme.typography.headlineMedium)
        Text("Enter your contact details. Your order will be ready for collection and held for 24 hours.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Card(shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("CONTACT DETAILS", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                CheckoutTextField(firstName, onFirstNameChanged, stringResource(R.string.yjijw_checkout_text_field_first_name), Modifier.fillMaxWidth())
                CheckoutTextField(lastName, onLastNameChanged, stringResource(R.string.yjijw_checkout_text_field_last_name), Modifier.fillMaxWidth())
                CheckoutTextField(
                    email,
                    onEmailChanged,
                    stringResource(R.string.yjijw_checkout_text_field_email),
                    Modifier.fillMaxWidth(),
                    isError = isEmailInvalid,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )
                if (isEmailInvalid) Text("Enter a valid email address", color = MaterialTheme.colorScheme.error)
            }
        }
        Card(shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("COLLECTION PROMISE", fontWeight = FontWeight.Bold)
                Text("We will prepare every item and keep the reservation available in store for the next 24 hours.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Button(onClick = onPlaceOrder, enabled = enabled, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text(stringResource(R.string.yjijw_button_confirm_order_label).uppercase())
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}
