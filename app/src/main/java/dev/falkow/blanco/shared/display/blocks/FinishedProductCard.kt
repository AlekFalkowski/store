package dev.falkow.blanco.shared.display.blocks

import android.text.Html
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.falkow.blanco.shared.types.FinishedProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishedProductCard(
    finishedProductCard: dev.falkow.blanco.shared.types.FinishedProductCard,
    onClick: () -> Unit
) {
    OutlinedCard(
        onClick = { onClick() },
        modifier = Modifier.aspectRatio(4f / 6f),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp),
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        border = BorderStroke(width = Dp.Hairline, color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(finishedProductCard.imageSrc)
                    .crossfade(true) // Плавная анимация при успешном выполнении запроса.
                    .build(),
                // model = productCard.imageSrc,
                // placeholder = "Loading",
                // error = painterResource(CoreR.drawable.ic_broken_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = "" + Html.fromHtml(finishedProductCard.title, 0),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "" + Html.fromHtml(finishedProductCard.price, 0) + "руб.",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.Bottom)
                    .padding(horizontal = 8.dp),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}