package br.me.vitorcsouza.bitfocus.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.me.vitorcsouza.bitfocus.ui.presentation.setup.EnergyLevel
import br.me.vitorcsouza.bitfocus.ui.theme.BorderGray
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.Periwinkle
import br.me.vitorcsouza.bitfocus.ui.theme.White

@Composable
fun BitTimerComponent(
    modifier: Modifier = Modifier,
    progress: Float,
    timerDisplay: String,
    isRunning: Boolean = false,
    accentColor: Color = ElectricCyan,
    energyLevel: EnergyLevel = EnergyLevel.MID,
) {
    val animatedColor by animateColorAsState(
        targetValue = if (progress < 0.1f && isRunning) Color(0xFFFF5252) else accentColor,
        animationSpec = tween(durationMillis = 1000),
        label = "ColorAnimation"
    )

    val animationDuration = when(energyLevel) {
        EnergyLevel.LOW -> 1500
        EnergyLevel.MID -> 1000
        EnergyLevel.HIGH -> 500
    }

    val infiniteTransition = rememberInfiniteTransition(label = "PulseTransition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (isRunning) 1.04f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(320.dp)
            .graphicsLayer(scaleX = pulseScale, scaleY = pulseScale)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidthPx = 8.dp.toPx()
            val glowRadiusPx = 20.dp.toPx()

            drawCircle(
                color = BorderGray.copy(alpha = 0.3f),
                style = Stroke(width = 2.dp.toPx()),
                radius = size.minDimension / 2.1f
            )

            drawIntoCanvas { canvas ->
                val paint = Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    style = android.graphics.Paint.Style.STROKE
                    this.strokeWidth = strokeWidthPx
                    strokeCap = android.graphics.Paint.Cap.ROUND
                    color = animatedColor.toArgb()
                    setShadowLayer(glowRadiusPx, 0f, 0f, animatedColor.toArgb())
                }

                canvas.nativeCanvas.drawArc(
                    glowRadiusPx + 10f,
                    glowRadiusPx + 10f,
                    size.width - (glowRadiusPx + 10f),
                    size.height - (glowRadiusPx + 10f),
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
                    fontWeight = FontWeight.Bold,
                    color = White,
                    fontSize = 72.sp,
                    letterSpacing = (-2).sp,
                    shadow = Shadow(
                        color = animatedColor.copy(alpha = 0.5f),
                        blurRadius = 30f
                    )
                ),
            )
            Text(
                text = "minutes",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Periwinkle,
                    fontSize = 18.sp,
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
        progress = 0.6f,
        timerDisplay = "25:00",
        isRunning = true,
        accentColor = ElectricCyan,
        energyLevel = EnergyLevel.HIGH
    )
}
