package hu.bme.aut.android.teacollector.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.teacollector.navigation.NavigationHandler
import hu.bme.aut.android.teacollector.screen.auth.AuthViewModel
import hu.bme.aut.android.teacollector.screen.cars.components.CarListBottomBar
import hu.bme.aut.android.teacollector.navigation.Screen

@Composable
fun ProfileScreen(
    navigationHandler: NavigationHandler,
    viewModel: AuthViewModel
) {
    val email = viewModel.getCurrentUserEmail()

    Scaffold(
        bottomBar = {
            CarListBottomBar(navigationHandler)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Logged in as:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = email ?: "Unknown user",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.logout()
                    navigationHandler.navigateToLogin()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log Out", color = Color.White)
            }
        }
    }
}
