package com.abhay.alumniconnect.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomChip(
    modifier: Modifier = Modifier,
    value: String,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    valueStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .clip(MaterialTheme.shapes.small)
            .background(color)
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Text(text = value, style = valueStyle)
    }
}
