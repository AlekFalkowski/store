package dev.falkow.blanco.shared.display.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Footer() {
    // val viewModel: FooterSectionViewModel = viewModel(factory = FooterSectionViewModel.Factory)
    val phoneNumber = ""
    Column() {
        HorizontalDivider(
            thickness = Dp.Hairline,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        val textModifier: Modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        Text(
            text = "В нашем магазине представлена только оригинальная сантехника немецкого производства BLANCO.",
            modifier = textModifier,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = phoneNumber,
            modifier = textModifier,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = "© 2024 GmbH + Co KG",
            modifier = textModifier,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}