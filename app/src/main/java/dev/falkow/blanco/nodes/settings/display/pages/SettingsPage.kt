package dev.falkow.blanco.nodes.settings.display.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.falkow.blanco.shared.display.templates.BasicPageTemplate
import dev.falkow.blanco.shared.display.sections.Title
import dev.falkow.blanco.shared.config.ColorSchemes
import dev.falkow.blanco.shared.config.ColorThemes

@Composable
internal fun SettingsPage(
    onSelectionChanged: (String) -> Unit = {},
    setPreferredAppColorScheme: (ColorSchemes) -> Unit = {},
    setPreferredAppColorTheme: (ColorThemes) -> Unit = {},
    setPreferredAppDynamicColor: (Boolean) -> Unit = {},
) {

    var selectedValue by rememberSaveable { mutableStateOf("") }

    BasicPageTemplate(
        secondColumnContent = { modifier: Modifier, contentMaxWidth ->
            Column(
                modifier = modifier.fillMaxSize(),
            ) {
                Title(title = "Settings page")

                Text(
                    text = "Color Scheme",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleSmall,
                    lineHeight = 48.sp,
                    modifier = Modifier.padding(horizontal = 28.dp)
                )

                ColorSchemes.entries.forEach {
                    Row(
                        modifier = Modifier.selectable(
                            selected = true,// selectedValue == item,
                            onClick = {
                                // selectedValue = item
                                // onSelectionChanged(item)
                            }
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = true,// selectedValue == item,
                            onClick = {
                                // selectedValue = item
                                // onSelectionChanged(item)
                            }
                        )
                        Text(it.name)
                    }
                }

                Text(
                    text = "Color Theme",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleSmall,
                    lineHeight = 48.sp,
                    modifier = Modifier.padding(horizontal = 28.dp)
                )

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "currentUser.email"
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "currentUser.name"
                )
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Change name")
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Change password")
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Sign out")
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Sign out of all devices")
                }

                // FooterSection()
            }
        }
    )
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
internal fun SettingsPagePreview() {
    SettingsPage()
}