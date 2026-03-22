package br.me.vitorcsouza.bitfocus.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
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
    isRunning: Boolean,
    accentColor: Color = ElectricCyan,
    modifier: Modifier = Modifier,
) {

    val animatedColor by animateColorAsState(
        targetValue = if (progress < 0.2f) Color.Red else accentColor,
        animationSpec = tween(1000),
        label = "colorTransition"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (isRunning) 1.03f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(320.dp)
            .graphicsLayer(scaleX = pulseScale, scaleY = pulseScale)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 12.dp.toPx()
            val radius = size.minDimension / 2.2f

            drawCircle(
                color = BorderGray.copy(alpha = 0.2f),
                style = Stroke(width = strokeWidth),
                radius = radius
            )

            drawArc(
                color = animatedColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = animatedColor.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth * 1.5f,
                    cap = StrokeCap.Round
                )
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = timerDisplay,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = White,
                    fontSize = 64.sp,
                    letterSpacing = (-2).sp,
                    shadow = Shadow(color = animatedColor, blurRadius = 20f)
                ),
            )
            Text(
                text = "minutes",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = SecondaryPeriwinkle,
                    fontSize = 16.sp,
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
        timerDisplay = "25:00",
        isRunning = true
    )
}
