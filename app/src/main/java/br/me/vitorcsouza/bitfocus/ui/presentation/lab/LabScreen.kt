package br.me.vitorcsouza.bitfocus.ui.presentation.lab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.me.vitorcsouza.bitfocus.ui.components.BentoCard
import br.me.vitorcsouza.bitfocus.ui.theme.DeepCharcoal
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.SecondaryPeriwinkle
import br.me.vitorcsouza.bitfocus.ui.theme.TextGray
import br.me.vitorcsouza.bitfocus.ui.theme.White

@Composable
fun LabScreen(viewModel: LabViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    LabContent(
        totalBits = state.totalBits,
        totalHours = state.totalHours,
        streakDays = state.streakDays,
        weeklyTrend = state.weeklyTrend,
        isLoading = state.isLoading,
    )
}

@Composable
fun LabContent(
    totalBits: Int,
    totalHours: Double,
    streakDays: Int,
    weeklyTrend: List<Int>,
    isLoading: Boolean,
) {
    Scaffold(containerColor = DeepCharcoal) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    "Focus Lab",
                    color = White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Your productivity insights",
                    color = SecondaryPeriwinkle
                )
            }

            BentoCard(modifier = Modifier.height(280.dp)) {
                Text(
                    "Weekly Trend",
                    color = White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Sessions completed this week",
                    color = SecondaryPeriwinkle,
                    style = MaterialTheme.typography.bodySmall
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    Text("Chart Placeholder", color = ElectricCyan)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BentoCard(modifier = Modifier.weight(1f)) {
                    Text(
                        "TOTAL BITS",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        "$totalBits",
                        color = ElectricCyan,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "sessions focused",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                BentoCard(modifier = Modifier.weight(1f)) {
                    Text("STREAK",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "$streakDays",
                        color = ElectricCyan,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "days in a row",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            BentoCard {
                Text(
                    "TOTAL FOCUS TIME",
                    color = SecondaryPeriwinkle,
                    style = MaterialTheme.typography.labelSmall
                )
                Row(verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = String.format(java.util.Locale.US, "%.1f", totalHours),
                        color = ElectricCyan,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        " hours",
                        color = SecondaryPeriwinkle,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Text(
                    "+12% from last week",
                    color = TextGray.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}

@Preview
@Composable
private fun LabScreenPreview() {

    LabContent(
        totalBits = 42,
        totalHours = 123.5,
        streakDays = 7,
        weeklyTrend = listOf(1, 2, 3, 4, 5, 6, 7),
        isLoading = false
    )

}