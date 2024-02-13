package dev.falkow.blanco.nodes.catalog.display.panels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.falkow.blanco.nodes.catalog.types.FieldSet
import dev.falkow.blanco.shared.display.utils.clearFocusOnKeyboardDismiss

@Composable
internal fun Filter(
    modifier: Modifier,
    filterConfig: List<FieldSet>,
    textFieldsStates: Map<String, MutableState<String>>,
    singleChoiceFieldsStates: Map<String, MutableState<String>>,
    multiChoiceFieldsStates: Map<String, MutableState<Set<String>>>,
    getDynamicContent: () -> Unit,
    resetFieldStates: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 4.dp)
    ) {

        filterConfig.forEachIndexed { index, fieldSet ->
            if (index > 0) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.padding(/*horizontal = 24.dp,*/ vertical = 8.dp)
                )
            }
            if (fieldSet.label != null) {
                Text(
                    text = fieldSet.label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleSmall,
                    lineHeight = 48.sp,
                    modifier = Modifier.padding(horizontal = 28.dp),
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }

            fieldSet.fieldList.forEach { field ->
                when (field.type) {
                    "multiChoice" -> {
                        Text(
                            modifier = Modifier.padding(horizontal = 28.dp),
                            lineHeight = 48.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.titleSmall,
                            text = field.label,
                        )
                        field.options?.forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .focusable(enabled = true)
                                    .clickable(
                                        onClick = {
                                            val newSet =
                                                multiChoiceFieldsStates[field.name]?.value?.toMutableSet()
                                                    ?: mutableSetOf()
                                            if (newSet.contains(option.value)) {
                                                newSet.remove(option.value)
                                            } else {
                                                newSet.add(option.value)
                                            }
                                            multiChoiceFieldsStates[field.name]?.value =
                                                newSet.toSet()
                                        },
                                    )
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    modifier = Modifier
                                        .padding(horizontal = 14.dp),
                                    checked = multiChoiceFieldsStates[field.name]
                                        ?.value?.contains(option.value) ?: false,
                                    onCheckedChange = null
                                )
                                Text(text = option.label)
                            }
                        }
                    }

                    "singleChoice" -> {
                        Text(
                            modifier = Modifier.padding(horizontal = 28.dp),
                            lineHeight = 48.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.titleSmall,
                            text = field.label,
                        )
                        field.options?.forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .focusable(enabled = true)
                                    .clickable(
                                        onClick = {
                                            singleChoiceFieldsStates[field.name]?.value =
                                                option.value
                                        },
                                    )
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    modifier = Modifier
                                        .padding(horizontal = 14.dp),
                                    selected = singleChoiceFieldsStates[field.name]?.value == option.value,
                                    onClick = null,
                                )
                                Text(text = option.label)
                            }
                        }
                    }

                    "range" -> {
                        Row {
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .weight(1.0F)
                                    .clearFocusOnKeyboardDismiss(),
                                singleLine = true,
                                minLines = 1,
                                maxLines = 1,
                                label = {
                                    Text(
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        text = "${field.label}, from"
                                    )
                                },
                                // placeholder = { Text(text = "Placeholder") },
                                value = textFieldsStates[field.name]?.value ?: "",
                                onValueChange = { textFieldsStates[field.name]?.value = it },
                            )
                            Text(text = "-")
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .weight(1.0F)
                                    .clearFocusOnKeyboardDismiss(),
                                singleLine = true,
                                minLines = 1,
                                maxLines = 1,
                                label = {
                                    Text(
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        text = "${field.label}, to"
                                    )
                                },
                                // placeholder = { Text(text = "Placeholder") },
                                value = textFieldsStates[field.endName]?.value ?: "",
                                onValueChange = { textFieldsStates[field.endName]?.value = it },
                            )
                        }
                    }

                    // "text"
                    else -> {
                        OutlinedTextField(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 12.dp),
                            label = {
                                Text(
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = field.label
                                )
                            },
                            // placeholder = { Text(text = "Placeholder") },
                            value = textFieldsStates[field.name]?.value ?: "",
                            onValueChange = { textFieldsStates[field.name]?.value = it },
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { getDynamicContent() },
        ) {
            Text(text = "Apply")
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { resetFieldStates() },
        ) {
            Text(text = "Reset")
        }
    }
}