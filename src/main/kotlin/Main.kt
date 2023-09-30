@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import partitionSetCreator.PartitionSetCreator
import ui.Input
import ui.Partition


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var text by remember { mutableStateOf("1, 2, 3") }
    var partitions by remember { mutableStateOf(emptySet<Set<Set<Int>>>()) }
    var tooLarge by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(top = 5.dp, start = 5.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                    Text("Set: { ", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.displaySmall)

                    Input(
                        value = text, onValueChange = { text = it },
                        singleLine = false,
                        modifier = Modifier.widthIn(0.dp, 400.dp)
                    )

                    Text(" }", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.displaySmall)

            }

            Button(
                content = { Text("All") },
                onClick = {
                    try {
                        val set = text.trim().split(", ").map { it.toInt() }.toHashSet()
                        if (set.size > 6) {
                            partitions = emptySet()
                            tooLarge = true
                            return@Button
                        } else {
                            tooLarge = false
                        }
                        val partSetCreatorEmpty = PartitionSetCreator(set)
                        partitions = partSetCreatorEmpty.findAllPartitions()
                    } catch (_: Exception) {
                        partitions = emptySet()
                    }
                }
            )

            Button(
                content = { Text("Even/Odd") },
                onClick = {
                    try {
                        val set = text.trim().split(", ").map { it.toInt() }.toHashSet()
                        val pars = set.partition { it%2==0 }
                        partitions = setOf(setOf(pars.first.toSet(), pars.second.toSet()))
                    } catch (_: Exception) {
                        partitions = emptySet()
                    }
                }
            )
        }

        Divider()

        // Info
        Row {
            SuggestionChip(
                colors = SuggestionChipDefaults.suggestionChipColors(
                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                label = { Text("Partitions: ${partitions.size}") },
                onClick = {},
            )
        }

        Divider(
            Modifier.padding(bottom = 10.dp)
        )

        SelectionContainer {
            if (!tooLarge || partitions.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    partitions.forEach {
                        Card {
                            Partition(it)
                        }
                    }
                }
            } else {
                Text(
                    "Set is too large",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            App()
        }
    }
}