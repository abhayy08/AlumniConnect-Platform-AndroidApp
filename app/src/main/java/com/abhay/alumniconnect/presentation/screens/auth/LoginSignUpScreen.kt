package com.abhay.alumniconnect.presentation.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.abhay.alumniconnect.presentation.components.CustomOutlinedTextField
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.example.compose.AlumniConnectTheme
import kotlinx.coroutines.launch

@Composable
fun LoginSignupScreen(
    uiState: AuthUiState = AuthUiState(),
    onEvent: (AuthUiActions) -> Unit = {},
    openAndPopUp: (Any, Any) -> Unit,
    isUserLoggedIn: Boolean
) {

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn) {
            openAndPopUp(Route.MainRoute, Route.AuthRoute)
        }
    }

    val options = listOf("Log In", "Sign Up")
    var selectedOption by remember { mutableStateOf(options[0]) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                onEvent(AuthUiActions.ClearSnackBarMessage)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .imePadding()
                .consumeWindowInsets(PaddingValues())
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginRegisterSwitch(
                modifier = Modifier.fillMaxWidth(0.8f),
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = {
                    selectedOption = it
                    onEvent(AuthUiActions.ResetState) // Reset state when switching
                })
            Spacer(Modifier.height(12.dp))

            AnimatedContent(
                targetState = selectedOption, transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(
                        animationSpec = tween(300)
                    )
                }, label = "LoginSignupTransition"
            ) { option ->
                if (option == options[0]) {
                    Login(
                        uiState = uiState, onEvent = onEvent, openAndPopUp = openAndPopUp
                    )
                } else {
                    SignUp(
                        uiState = uiState, onEvent = onEvent, openAndPopUp = openAndPopUp
                    )
                }
            }

        }
    }
}

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    onEvent: (AuthUiActions) -> Unit,
    openAndPopUp: (Any, Any) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(18.dp))
        Text("Sign Up", style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(18.dp))

        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomOutlinedTextField(
                value = uiState.name,
                onValueChange = { onEvent(AuthUiActions.onNameChange(it)) },
                label = "Name",
                isRequired = true,
                error = uiState.nameError
            )

            CustomOutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(AuthUiActions.onEmailChange(it)) },
                label = "Email",
                isRequired = true,
                error = uiState.emailError
            )

            CustomOutlinedTextField(
                value = uiState.password,
                onValueChange = { onEvent(AuthUiActions.onPasswordChange(it)) },
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                isRequired = true,
                error = uiState.passwordError
            )

            CustomOutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = { onEvent(AuthUiActions.onConfirmPasswordChange(it)) },
                label = "Confirm Password",
                visualTransformation = PasswordVisualTransformation(),
                isRequired = true,
                error = uiState.confirmPasswordError
            )

            CustomOutlinedTextField(
                value = uiState.graduationYear,
                onValueChange = { onEvent(AuthUiActions.onGraduationYearChange(it)) },
                label = "Graduation Year",
                isRequired = true,
                error = uiState.graduationYearError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            CustomOutlinedTextField(
                value = uiState.university,
                onValueChange = { onEvent(AuthUiActions.onUniversityChange(it)) },
                label = "University",
                isRequired = true,
                error = uiState.universityError
            )

            CustomOutlinedTextField(
                value = uiState.degree,
                onValueChange = { onEvent(AuthUiActions.onDegreeChange(it)) },
                label = "Degree",
                isRequired = true,
                error = uiState.degreeError
            )

            CustomOutlinedTextField(
                value = uiState.major,
                onValueChange = { onEvent(AuthUiActions.onMajorChange(it)) },
                label = "Major",
                isRequired = true,
                error = uiState.majorError
            )

            Button(
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = MaterialTheme.shapes.small,
                onClick = { onEvent(AuthUiActions.SignUp(openAndPopUp)) },
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
                )
            ) {
                Text("Sign Up")
            }
        }
    }
}


@Composable
fun Login(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    onEvent: (AuthUiActions) -> Unit,
    openAndPopUp: (Any, Any) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(18.dp))
        Text("Login", style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(18.dp))

        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomOutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(AuthUiActions.onEmailChange(it)) },
                label = "Email",
                error = uiState.emailError
            )

            CustomOutlinedTextField(
                value = uiState.password,
                onValueChange = { onEvent(AuthUiActions.onPasswordChange(it)) },
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                error = uiState.passwordError
            )

            Button(
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = MaterialTheme.shapes.small,
                onClick = { onEvent(AuthUiActions.Login(openAndPopUp)) },
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
                )
            ) {
                Text("Login")
            }
        }
    }
}


@Composable
fun LoginRegisterSwitch(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String = "Log In",
    onOptionSelected: (String) -> Unit
) {

    val selectedIndex = options.indexOf(selectedOption).coerceAtLeast(0)

    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier
            .height(48.dp)
            .shadow(6.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small),
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        indicator = { tabPositions ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .fillMaxHeight()
                    .zIndex(-1f)
                    .padding(5.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.small
                    )
            )
        },
        divider = {}) {
        options.forEachIndexed { index, option ->
            Tab(selected = selectedIndex == index, onClick = { onOptionSelected(option) }, text = {
                Text(
                    text = option,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (selectedIndex == index) Color.White else Color.Gray,
                    fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal
                )
            })
        }
    }
}

@Preview
@Composable
private fun LoginPreview() {
    AlumniConnectTheme {
        LoginSignupScreen(isUserLoggedIn = false, openAndPopUp = { _, _ -> })
    }
}