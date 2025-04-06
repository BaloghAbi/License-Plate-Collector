package hu.bme.aut.android.teacollector.screen.cars.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.teacollector.navigation.NavigationHandler
import hu.bme.aut.android.teacollector.ui.theme.TEAGreen

@Composable
fun CarListBottomBar(navigationHandler: NavigationHandler) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = TEAGreen) },
            label = { Text("Home") },
            selected = false,
            onClick = {navigationHandler.navigateToHome()}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Map", tint = TEAGreen) },
            label = { Text("Map") },
            selected = false,
            onClick = { navigationHandler.navigateToMap() }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = TEAGreen) },
            label = { Text("Profile") },
            selected = false,
            onClick = { navigationHandler.navigateToProfile() }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings", tint = TEAGreen) },
            label = { Text("Settings") },
            selected = false,
            onClick = { navigationHandler.navigateToSettings() }
        )
    }
}

