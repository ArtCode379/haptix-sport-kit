package haptixgolf.sports.haptixsportkit.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.SportsGolf
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import haptixgolf.sports.haptixsportkit.R

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val supportUrl = stringResource(R.string.yjijw_customer_support_link)
    Column(modifier = modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Icon(Icons.Rounded.SportsGolf, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text("HAPTIX SETTINGS", style = MaterialTheme.typography.headlineMedium)
        Text("ABOUT", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
        Card(shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                SettingRow(Icons.Rounded.Business, stringResource(R.string.yjijw_settings_screen_company_label), stringResource(R.string.yjijw_company_name))
                SettingRow(Icons.Rounded.SportsGolf, stringResource(R.string.yjijw_settings_screen_version_label), stringResource(R.string.yjijw_app_version))
            }
        }
        Text("SUPPORT", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
        Text("Questions about a product or reservation? Our team is ready to help.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Button(
            onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(supportUrl))) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Rounded.Language, contentDescription = null)
            Text("  ${stringResource(R.string.yjijw_settings_screen_customer_support_label)}")
        }
        Text(
            text = "Product information and availability may change. Your reservation is held in store for 24 hours.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SettingRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Column(Modifier.padding(start = 12.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium)
            Text(value, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.weight(1f))
    }
}
