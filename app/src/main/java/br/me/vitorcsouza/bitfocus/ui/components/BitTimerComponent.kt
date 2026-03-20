package br.me.vitorcsouza.bitfocus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.me.vitorcsouza.bitfocus.ui.theme.BorderGray
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.SecondaryPeriwinkle
import br.me.vitorcsouza.bitfocus.ui.theme.White

@Composable
fun BitTimerComponent(
    progress: Float,
    timerDisplay: String,
    modifier: Modifier = Modifier,
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.size(320.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidthPx = 6.dp.toPx()
            val glowRadiusPx = 15.dp.toPx()

            drawCircle(
                color = BorderGray.copy(alpha = 0.5f),
                style = Stroke(width = 2.dp.toPx()),
                radius = size.minDimension / 2.1f
            )

            drawIntoCanvas { canvas ->
                val paint = Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    style = android.graphics.Paint.Style.STROKE
                    this.strokeWidth = strokeWidthPx
                    strokeCap = android.graphics.Paint.Cap.ROUND
                    color = ElectricCyan.toArgb()
                    // Efeito de brilho neon
                    setShadowLayer(glowRadiusPx, 0f, 0f, ElectricCyan.toArgb())
                }

                canvas.nativeCanvas.drawArc(
                    glowRadiusPx,
                    glowRadiusPx,
                    size.width - glowRadiusPx,
                    size.height - glowRadiusPx,
                    -90f,
                    progress * 360f,
                    false,
                    paint
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = timerDisplay,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Light,
                    color = White,
                    fontSize = 84.sp,
                    letterSpacing = (-2).sp
                ),
            )
            Text(
                text = "minutes",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = SecondaryPeriwinkle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0E14)
@Composable
private fun BitTimerComponentPreview() {
    BitTimerComponent(
        progress = 0.8f,
        timerDisplay = "25:00"
    )
}
