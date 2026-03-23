package br.me.vitorcsouza.bitfocus.ui.presentation.lab

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.me.vitorcsouza.bitfocus.domain.model.SessionCategory
import br.me.vitorcsouza.bitfocus.ui.components.BentoCard
import br.me.vitorcsouza.bitfocus.ui.components.TimeCostChart
import br.me.vitorcsouza.bitfocus.ui.components.WeeklyTrendChart
import br.me.vitorcsouza.bitfocus.ui.theme.*

@Composable
fun LabScreen(
    onBack: () -> Unit,
    viewModel: LabViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LabContent(
        totalBits = state.totalBits,
        totalHours = state.totalHours,
        weeklyTrend = state.weeklyTrend,
        categoryStats = state.categoryStats,
        isLoading = state.isLoading,
        onBack = onBack
    )
}

@Composable
fun LabContent(
    totalBits: Int,
    totalHours: Double,
    weeklyTrend: List<Int>,
    categoryStats: List<CategoryStat>,
    isLoading: Boolean,
    onBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(containerColor = DeepCharcoal) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
                Column {
                    Text(
                        text = "The Lab",
                        color = White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Your cognitive performance insights",
                        color = SecondaryPeriwinkle,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "Total Bits",
                    value = totalBits.toString(),
                    color = ElectricCyan
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "Focus Hours",
                    value = String.format("%.1f", totalHours),
                    color = VibrantPurple
                )
            }

            // Weekly Trend Chart
            BentoCard {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Weekly Focus Trend",
                        color = White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    WeeklyTrendChart(
                        data = weeklyTrend,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }

            BentoCard {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column {
                        Text(
                            text = "Time Investment",
                            color = White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Distribution by life category",
                            color = SecondaryPeriwinkle,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (categoryStats.isEmpty() && !isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No data yet. Start a session!", color = BorderGray)
                        }
                    } else {
                        TimeCostChart(
                            stats = categoryStats,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                        )
                    }
                }
            }

            // Legend
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                categoryStats.forEach { stat ->
                    CategoryLegendItem(stat)
                }
            }
        }
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    BentoCard(modifier = modifier) {
        Column {
            Text(label, color = SecondaryPeriwinkle, style = MaterialTheme.typography.labelMedium)
            Text(
                value,
                color = color,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CategoryLegendItem(stat: CategoryStat) {
    val color = when(stat.category) {
        SessionCategory.WORK -> ElectricCyan
        SessionCategory.STUDY -> SoftBlue
        SessionCategory.LEISURE -> VibrantPurple
        SessionCategory.OTHER -> TextGray
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color, MaterialTheme.shapes.small)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stat.category.name.lowercase().replaceFirstChar { it.uppercase() },
                color = White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = "${stat.totalMinutes}m (${(stat.percentage * 100).toInt()}%)",
            color = SecondaryPeriwinkle,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0E14)
@Composable
fun LabContentPreview() {
    BitFocusTheme {
        LabContent(
            totalBits = 142,
            totalHours = 68.5,
            weeklyTrend = listOf(4, 6, 5, 8, 7, 9, 6),
            categoryStats = listOf(
                CategoryStat(SessionCategory.WORK, 1200, 0.4f),
                CategoryStat(SessionCategory.STUDY, 900, 0.3f),
                CategoryStat(SessionCategory.LEISURE, 600, 0.2f),
                CategoryStat(SessionCategory.OTHER, 300, 0.1f)
            ),
            isLoading = false,
            onBack = {}
        )
    }
}
