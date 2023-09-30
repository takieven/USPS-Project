package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipBorder
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Partition(partition: Set<Set<Int>>) {
    val colors = listOf(
        Color(93, 18, 210),
        Color(185, 49, 252),
        Color(255, 106, 194)
    )
    var index = 0
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.5.dp)
        ) {
            partition.forEach {
                if (index == 3) {
                    index = 0
                }
                Surface(
                    color = colors[index],
                    shape = MaterialTheme.shapes.extraSmall,
                ) {
                    val part = StringBuilder()
                    part.append("{ ")
                    val elements = it.iterator()
                    while (elements.hasNext()) {
                        part.append("${elements.next()}, ")
                    }
                    part.deleteCharAt(part.length - 2)
                    part.append("}")
                    Text(
                        part.toString(),
                        modifier = Modifier
                            .padding(top = 0.dp, bottom = 2.5.dp, end = 2.dp, start = 2.dp),
                        color = MaterialTheme.colorScheme.surface
                    )

                }
                index++
            }
        }
}