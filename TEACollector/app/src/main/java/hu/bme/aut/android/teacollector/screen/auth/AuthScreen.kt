package hu.bme.aut.android.teacollector.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.teacollector.ui.theme.TEAGreen

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory),
    onAuthSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize() // <- This makes it take the full screen
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome!", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
/*
* colors = ButtonDefaults.buttonColors(
                            containerColor = TEAGreen
                        ))*/
        Row {
            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = TEAGreen)
            ) {
                Text("Login")
            }

            /*Spacer(Modifier.width(8.dp))

            Button(
                onClick = { viewModel.register(email, password) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Register")
            }*/
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        uiState.error?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
        }
    }

    if (uiState.isSuccess) {
        // Navigate to CarList
        onAuthSuccess()
    }
}
