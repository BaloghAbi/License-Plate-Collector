package hu.bme.aut.android.teacollector.feature.map

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import hu.bme.aut.android.teacollector.feature.cars.components.CarListBottomBar
import hu.bme.aut.android.teacollector.navigation.Screen

@Composable
fun MapScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            CarListBottomBar(
                onMapClick = { navController.navigate(Screen.MainMap.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onHomeClick = { navController.navigate(Screen.CarList.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }
    ){innerPadding ->
        Text(
            text = "Map placeholder",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun PreviewMapScreen(){}
