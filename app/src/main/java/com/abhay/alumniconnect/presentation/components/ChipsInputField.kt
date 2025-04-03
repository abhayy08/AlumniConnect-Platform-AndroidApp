package com.abhay.alumniconnect.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AlumniConnectTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsInputField(
    label: String,
    initialValue: List<String> = emptyList(),
    onValueChanged: (List<String>) -> Unit
) {
    val value = remember(initialValue) { mutableStateListOf<String>().apply { addAll(initialValue) } }

    var inputValue by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isFocused)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .clip(MaterialTheme.shapes.small)
                .clickable { focusRequester.requestFocus() }
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                // Display existing skills as chips
                value.forEach { skill ->
                    CustomChip(
                        label = skill,
                        onDelete = {
                            value.remove(skill)
                            onValueChanged(value.toList())
                        }
                    )
                }

                // Input field with + icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp)
                ) {
                    if (inputValue.text.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Skill",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    BasicTextField(
                        value = inputValue,
                        onValueChange = { inputValue = it },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (inputValue.text.isNotBlank()) {
                                    addSkill(inputValue.text.trim(), value, onValueChanged)
                                    inputValue = TextFieldValue("")
                                }
                            }
                        ),
                        modifier = Modifier
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Backspace &&
                                    keyEvent.type == KeyEventType.KeyDown &&
                                    inputValue.text.isEmpty() &&
                                    value.isNotEmpty()
                                ) {
                                    value.removeAt(value.lastIndex)
                                    onValueChanged(value.toList())
                                    true
                                } else {
                                    false
                                }
                            }
                            .onFocusChanged { isFocused = it.isFocused }
                            .focusRequester(focusRequester)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomChip(label: String, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(start = 10.dp, top = 6.dp, bottom = 6.dp, end = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove $label",
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onDelete() },
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun addSkill(skill: String, skills: MutableList<String>, onSkillsChanged: (List<String>) -> Unit) {
    if (skill.isNotBlank() && !skills.contains(skill)) {
        skills.add(skill)
        onSkillsChanged(skills.toList())
    }
}

@Preview(showBackground = true)
@Composable
fun SkillsInputFieldPreview() {
    val initialSkills = listOf("Kotlin", "Jetpack Compose", "Android Development")
    var skills by remember { mutableStateOf(initialSkills) }

    AlumniConnectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                ChipsInputField(
                    initialValue = skills,
                    onValueChanged = { updatedSkills ->
                        skills = updatedSkills
                    },
                    label = "Skills"
                )
            }
        }
    }
}
