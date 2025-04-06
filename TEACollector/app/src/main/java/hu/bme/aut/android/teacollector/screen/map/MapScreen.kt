package hu.bme.aut.android.teacollector.screen.map

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import hu.bme.aut.android.teacollector.navigation.NavigationHandler
import hu.bme.aut.android.teacollector.screen.cars.components.CarListBottomBar
import hu.bme.aut.android.teacollector.navigation.Screen

@Composable
fun MapScreen(navigationHandler: NavigationHandler) {
    Scaffold(
        bottomBar = {
            CarListBottomBar(navigationHandler)
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
