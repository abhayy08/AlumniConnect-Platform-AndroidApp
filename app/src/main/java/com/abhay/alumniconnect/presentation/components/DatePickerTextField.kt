package com.abhay.alumniconnect.presentation.components

import android.app.DatePickerDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.abhay.alumniconnect.utils.formatDateForDisplay
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DatePickerTextField(
    label: String,
    value: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
     isError: Boolean = false,
) {
    val context = LocalContext.current

    // Initialize with current date/time if no value, otherwise parse existing value
    val initialDateTime = remember {
        if (value.isEmpty()) {
            ZonedDateTime.now(ZoneOffset.UTC)
        } else {
            try {
                ZonedDateTime.parse(value)
            } catch (e: Exception) {
                ZonedDateTime.now(ZoneOffset.UTC)
            }
        }
    }

    // Track year, month, day separately to update the date picker dialog
    var selectedYear = remember { mutableStateOf(initialDateTime.year) }
    var selectedMonth = remember { mutableStateOf(initialDateTime.monthValue - 1) }
    var selectedDayOfMonth = remember { mutableStateOf(initialDateTime.dayOfMonth) }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedYear.value = year
                selectedMonth.value = month
                selectedDayOfMonth.value = dayOfMonth

                // Create date with selected values and current time
                val selectedDateTime = ZonedDateTime.of(
                    year,
                    month + 1, // Convert back to 1-based month
                    dayOfMonth,
                    0, // Hour
                    0, // Minute
                    0, // Second
                    0, // Nanosecond
                    ZoneOffset.UTC
                )

                // Format to ISO 8601 string
                val formattedDate = selectedDateTime.format(DateTimeFormatter.ISO_INSTANT)

                // Call the callback with the selected date
                onDateSelected(formattedDate)
            },
            selectedYear.value,
            selectedMonth.value,
            selectedDayOfMonth.value
        )
    }

    CustomOutlinedTextField(
        value = if (value.isEmpty()) value else formatDateForDisplay(value),
        onValueChange = {},
        label = label,
        readOnly = true,
        modifier = modifier,
        trailingIcon = {
            IconButton(
                onClick = { datePickerDialog.show() }
            ) {
                Icon(imageVector = Icons.Rounded.CalendarMonth, contentDescription = "Select Date")
            }
        },
        isError = isError
    )
}
