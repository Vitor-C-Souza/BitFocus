package br.me.vitorcsouza.bitfocus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.bitfocus.ui.theme.BorderGray
import br.me.vitorcsouza.bitfocus.ui.theme.DarkSlate
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.Periwinkle

@Composable
fun BitFocusIcon(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(28.dp))
            .background(DarkSlate)
            .border(1.dp, BorderGray, RoundedCornerShape(28.dp))
            .drawWithContent {
                // Gradient Overlay (Radial)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            ElectricCyan.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = center.copy(x = size.toPx() * 0.3f, y = size.toPx() * 0.3f),
                        radius = size.toPx() * 0.7f
                    )
                )
                drawContent()
            },
        contentAlignment = Alignment.Center
    ) {
        val iconContentSize = size * 0.55f
        
        Canvas(modifier = Modifier.size(iconContentSize)) {
            val canvasSize = this.size.width
            val center = this.size.width / 2f
            
            // Outer Ring - Bit Pattern (Dashed)
            drawCircle(
                color = ElectricCyan.copy(alpha = 0.6f),
                radius = canvasSize * (28f / 64f),
                style = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 4.dp.toPx()), 0f)
                )
            )
            
            // Middle Ring
            drawCircle(
                color = Periwinkle.copy(alpha = 0.4f),
                radius = canvasSize * (20f / 64f),
                style = Stroke(width = 2.dp.toPx())
            )
            
            // Inner Focus Dot with "Glow" (Simulated with layers)
            drawCircle(
                color = ElectricCyan.copy(alpha = 0.2f),
                radius = canvasSize * (10f / 64f)
            )
            drawCircle(
                color = ElectricCyan,
                radius = canvasSize * (6f / 64f)
            )
            
            // Four Corner Bits
            val bitRadius = canvasSize * (2.5f / 64f)
            val bitOpacity = 0.8f
            val bitColor = ElectricCyan.copy(alpha = bitOpacity)
            
            // Top
            drawCircle(bitColor, bitRadius, center = this.center.copy(y = canvasSize * (10f / 64f)))
            // Bottom
            drawCircle(bitColor, bitRadius, center = this.center.copy(y = canvasSize * (54f / 64f)))
            // Left
            drawCircle(bitColor, bitRadius, center = this.center.copy(x = canvasSize * (10f / 64f)))
            // Right
            drawCircle(bitColor, bitRadius, center = this.center.copy(x = canvasSize * (54f / 64f)))
        }
    }
}

@Preview
@Composable
private fun BitFocusIconPreview() {
    BitFocusIcon()
}
