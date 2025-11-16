package dev.bober.store.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bober.store.domain.SendingState
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    toLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var buttonsEnabled by remember { mutableStateOf(true) }

    val registrationState by viewModel.registerState.collectAsState()
    val confirmState by viewModel.confirmState.collectAsState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Регистрация",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Фамилия") },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff


                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        AnimatedVisibility(
            visible = password.isNotEmpty()
        ) {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Повторите пароль") },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                trailingIcon = {
                    val image = if (confirmPasswordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                isError = (password != confirmPassword) && confirmPassword.isNotEmpty()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.register(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    passwordConfirmation = confirmPassword
                )
                buttonsEnabled = false
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            enabled = buttonsEnabled
        ) {
            Text(
                text = "Зарегистрироваться",
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = toLogin,
            enabled = buttonsEnabled
        ) {
            Text(
                text = "Уже есть аккаунт? Войти",
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }

    when (registrationState) {
        SendingState.LOADING -> {
            buttonsEnabled = false
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }

        SendingState.SUCCESS -> {
            var code by remember { mutableStateOf("") }
            var timerSeconds by remember { mutableStateOf(60) }
            var isTimerRunning by remember { mutableStateOf(true) }

            LaunchedEffect(key1 = isTimerRunning) {
                if (isTimerRunning) {
                    while (timerSeconds > 0) {
                        delay(1000)
                        timerSeconds--
                    }
                    isTimerRunning = false
                }
            }

            AlertDialog(
                onDismissRequest = {},
                title = {
                    Text(
                        text = "Введите код регистрации"
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = code,
                            onValueChange = { code = it },
                            shape = CircleShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                                cursorColor = MaterialTheme.colorScheme.secondary,
                                focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (isTimerRunning) {
                            Text(
                                text = "Отправить код повторно можно через $timerSeconds секунд",
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        } else {
                            TextButton(
                                onClick = {
                                    viewModel.resendConfirmation(email)
                                    timerSeconds = 60
                                    isTimerRunning = true
                                }
                            ) {
                                Text(
                                    text = "Отправить код повторно",
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.confirmRegistration(code)
                        }
                    ) {
                        Text(
                            text = "Отправить",
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            )
        }

        SendingState.ERROR -> {
            buttonsEnabled = true
        }

        SendingState.NONE -> {
            buttonsEnabled = true
        }
    }

    when (confirmState) {
        SendingState.NONE -> {
            buttonsEnabled = true
        }
        SendingState.LOADING -> {
            buttonsEnabled = false
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
        SendingState.ERROR -> {
            buttonsEnabled = true
        }
        SendingState.SUCCESS -> {
            toLogin()
        }
    }
}
