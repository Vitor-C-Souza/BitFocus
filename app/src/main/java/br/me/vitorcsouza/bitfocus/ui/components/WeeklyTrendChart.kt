package br.me.vitorcsouza.bitfocus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.bitfocus.ui.theme.BorderGray
import br.me.vitorcsouza.bitfocus.ui.theme.DeepCharcoal
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan

@Composable
fun WeeklyTrendChart(
    data: List<Int>,
    modifier: Modifier = Modifier
) {

    val graphColor = ElectricCyan

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        val width = size.width
        val height = size.height
        val maxData = data.maxOrNull()?.takeIf { it > 0 }?.toFloat() ?: 1f

        val points = data.indices.map { i ->
            val x = i * (width / (data.size - 1))
            val fraction = data[i] / maxData
            val y = height - (fraction * height)
            Offset(x, y)
        }

        val gridLines = 4
        for (i in 0..gridLines) {
            val y = i * (height / gridLines)
            drawLine(
                color = BorderGray.copy(alpha = 0.2f),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        val strokePath = Path().apply {
            points.forEachIndexed { index, point ->
                if (index == 0) moveTo(point.x, point.y)
                else lineTo(point.x, point.y)
            }
        }

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        points.forEach { point ->
            drawCircle(
                color = DeepCharcoal,
                radius = 6.dp.toPx(),
                center = point
            )

            drawCircle(
                color = graphColor,
                radius = 4.dp.toPx(),
                center = point,
                style = Stroke(width = 2.dp.toPx())
            )
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0E14)
@Composable
private fun WeeklyTrendChartPreview() {
    WeeklyTrendChart(
        data = listOf(3, 5, 2, 8, 6, 4, 7)
    )
}