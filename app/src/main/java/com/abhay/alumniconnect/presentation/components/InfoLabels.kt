package com.abhay.alumniconnect.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun InfoLabel(
    modifier: Modifier = Modifier,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    value: String = "",
    labelColor: Color = Color.Gray,
    valueStyle: TextStyle = MaterialTheme.typography.bodySmall
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.Center
    ) {
        Text(label, style = labelStyle, color = labelColor)
        if(value.isNotEmpty()) Text(value, style = valueStyle)
    }
}

@Composable
fun InfoLabelWithChip(
    modifier: Modifier = Modifier,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    value: String = "",
    labelColor: Color = Color.Gray,
    chipColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    valueStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.Center
    ) {
        Text(label, style = labelStyle, color = labelColor)
        if (value.isNotEmpty()) {
            CustomChip(
                value = value,
                color = chipColor,
                valueStyle = valueStyle
            )
        }
    }
}