package br.me.vitorcsouza.bitfocus.ui.presentation.completion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.bitfocus.ui.components.BentoCard
import br.me.vitorcsouza.bitfocus.ui.components.BitFocusButton
import br.me.vitorcsouza.bitfocus.ui.theme.BorderGray
import br.me.vitorcsouza.bitfocus.ui.theme.DeepCharcoal
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.SecondaryPeriwinkle
import br.me.vitorcsouza.bitfocus.ui.theme.White

@Composable
fun CompletionScreen(
    state: CompletionStates,
    onClose: () -> Unit
) {
    CompletionScreenContent(
        state = state,
        onClose = onClose
    )
}

@Composable
fun CompletionScreenContent(
    state: CompletionStates,
    onClose: () -> Unit
) {
    Scaffold(containerColor = DeepCharcoal) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("BITFOCUS", color = BorderGray, style = MaterialTheme.typography.labelSmall)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(2.dp, ElectricCyan, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check Icon",
                    tint = ElectricCyan,
                    modifier = Modifier.size(34.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Session Complete",
                    style = MaterialTheme.typography.displaySmall,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Excellent focus. Well done.",
                    color = SecondaryPeriwinkle,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            BentoCard {
                SummaryRow(label = "Duration", value = state.durationDisplay)
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = BorderGray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Goal",
                            color = SecondaryPeriwinkle,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            "Completed",
                            color = SecondaryPeriwinkle,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        text = state.goalName,
                        color = White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = BorderGray)
                SummaryRow(label = "Bits Earned", value = state.bitsEarned, valueColor = ElectricCyan)
            }
            Surface(
                color = ElectricCyan.copy(alpha = 0.05f),
                border = BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = null,
                        tint = Color(0xFFFFB74D)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("Streak Maintained", color = ElectricCyan, fontWeight = FontWeight.Bold)
                        Text("${state.streakCount} days in a row", color = SecondaryPeriwinkle, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            BitFocusButton(text = "Finish", onClick = onClose)
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, valueColor: Color = White) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = SecondaryPeriwinkle)
        Text(value, color = valueColor, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun CompletionScreenPreview() {
    CompletionScreenContent(
        state = CompletionStates(
            durationDisplay = "25m",
            goalName = "Coding",
            bitsEarned = "1",
            streakCount = 5
        ),
        onClose = {}
    )
}
