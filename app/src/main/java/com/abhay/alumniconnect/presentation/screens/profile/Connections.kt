package com.abhay.alumniconnect.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.presentation.dummyUser
import com.example.compose.AlumniConnectTheme
import com.example.ui.theme.AppShapes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionsScreen(connections: List<Connection>, onBackClick: () -> Unit) {

    LazyColumn() {
        items(connections) { connection ->
            ConnectionItem(connection)
        }
    }

}

@Composable
fun ConnectionItem(connection: Connection) {
    Card(
        shape = AppShapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = connection.name.first().toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = connection.name, fontWeight = FontWeight.Bold)
                connection.company?.let { Text(text = it, color = Color.Gray, fontSize = 14.sp) }
                connection.jotTitle?.let { Text(text = it, color = Color.Gray, fontSize = 12.sp) }
            }
        }
    }
}

@Preview
@Composable
private fun ConnectionScreenPreview() {
    AlumniConnectTheme {
        ConnectionsScreen(dummyUser.connections) { }
    }
}
