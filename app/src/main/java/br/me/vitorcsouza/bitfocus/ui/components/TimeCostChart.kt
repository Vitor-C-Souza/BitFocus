package br.me.vitorcsouza.bitfocus.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import br.me.vitorcsouza.bitfocus.domain.model.SessionCategory
import br.me.vitorcsouza.bitfocus.ui.presentation.lab.CategoryStat
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.SoftBlue
import br.me.vitorcsouza.bitfocus.ui.theme.TextGray
import br.me.vitorcsouza.bitfocus.ui.theme.VibrantPurple

@Composable
fun TimeCostChart(
    stats: List<CategoryStat>,
    modifier: Modifier = Modifier
) {
    val transitionProgress = remember { Animatable(0f) }

    LaunchedEffect(stats) {
        transitionProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 35f
            val radius = size.minDimension / 2 - strokeWidth
            val center = Offset(size.width / 2, size.height / 2)
            
            var startAngle = -90f

            stats.forEach { stat ->
                val sweepAngle = stat.percentage * 360f * transitionProgress.value
                val color = when (stat.category) {
                    SessionCategory.WORK -> ElectricCyan
                    SessionCategory.STUDY -> SoftBlue
                    SessionCategory.LEISURE -> VibrantPurple
                    SessionCategory.OTHER -> TextGray
                }

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                startAngle += sweepAngle
            }
        }
    }
}
