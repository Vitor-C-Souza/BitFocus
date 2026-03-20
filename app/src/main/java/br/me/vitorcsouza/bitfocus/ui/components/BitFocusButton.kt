package br.me.vitorcsouza.bitfocus.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.bitfocus.ui.theme.DeepCharcoal
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan

@Composable
fun BitFocusButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ElectricCyan),
        shape = RoundedCornerShape(28.dp)
    ) {
        Text(text = text, color = DeepCharcoal, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
private fun BitFocusButtonPreview() {
    BitFocusButton(
        onClick = {},
        text = "Start"
    )
}