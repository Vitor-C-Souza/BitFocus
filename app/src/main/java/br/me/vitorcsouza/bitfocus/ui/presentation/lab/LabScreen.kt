package br.me.vitorcsouza.bitfocus.ui.presentation.lab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.me.vitorcsouza.bitfocus.ui.components.BentoCard
import br.me.vitorcsouza.bitfocus.ui.components.WeeklyTrendChart
import br.me.vitorcsouza.bitfocus.ui.theme.DeepCharcoal
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.SecondaryPeriwinkle
import br.me.vitorcsouza.bitfocus.ui.theme.White

@Composable
fun LabScreen(
    viewModel: LabViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {

    val state by viewModel.state.collectAsState()

    LabContent(
        totalBits = state.totalBits,
        totalHours = state.totalHours,
        streakDays = state.streakDays,
        weeklyTrend = state.weeklyTrend,
        isLoading = state.isLoading,
        onBack = onBack
    )
}

@Composable
fun LabContent(
    totalBits: Int,
    totalHours: Double,
    streakDays: Int,
    weeklyTrend: List<Int>,
    isLoading: Boolean,
    onBack: () -> Unit = {}
) {
    Scaffold(containerColor = DeepCharcoal) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.clickable { onBack() }
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Focus Lab",
                        color = White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Your productivity insights",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            BentoCard(modifier = Modifier.height(300.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "Weekly Trend",
                        color = White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Sessions completed this week",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                WeeklyTrendChart(
                    data = weeklyTrend,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                    days.forEach { day ->
                        Text(day, color = SecondaryPeriwinkle, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                    }
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
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "$totalBits",
                        color = ElectricCyan,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "sessions\nfocused",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 16.sp
                    )
                }
                BentoCard(modifier = Modifier.weight(1f)) {
                    Text("STREAK",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "$streakDays",
                        color = ElectricCyan,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "days in\na row",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 16.sp
                    )
                }
            }

            BentoCard {
                Text(
                    "TOTAL FOCUS TIME",
                    color = SecondaryPeriwinkle,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = String.format(java.util.Locale.US, "%.1f", totalHours),
                        color = ElectricCyan,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "hours",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                Text(
                    text = "+12% from last week",
                    color = SecondaryPeriwinkle.copy(alpha = 0.6f),
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
        totalBits = 142,
        totalHours = 68.5,
        streakDays = 12,
        weeklyTrend = listOf(4, 6, 5, 8, 7, 9, 6),
        isLoading = false
    )
}
