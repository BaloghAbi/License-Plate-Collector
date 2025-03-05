package hu.bme.aut.android.teacollector.feature.cars

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import hu.bme.aut.android.teacollector.data.car.model.CarItem
import hu.bme.aut.android.teacollector.feature.cars.components.CarItemCard
import hu.bme.aut.android.teacollector.feature.cars.components.CarItemDialog
import hu.bme.aut.android.teacollector.feature.cars.components.CarListBottomBar
import hu.bme.aut.android.teacollector.feature.cars.components.CarListTopBar


@Composable
fun CarsScreen() {

    val list = listOf(
        CarItem(
            name = "TEA-123",
            desciption = "szép rendszám",
            isCollected = true,
            long = 10.1,
            lat = 10.2
        ),
        CarItem(
            name = "TEA-321",
            desciption = "szép rendszám",
            isCollected = true,
            long = 11.1,
            lat = 11.2
        )
    )

    var isDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CarListTopBar(
                onMenuClick = {/*TODO*/},
                onSearchQueryChange = {/*TODO*/}
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    isDialogOpen = true
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new item"
                )
            }
        },
        bottomBar = {
            CarListBottomBar(
                onMapClick = {/*TODO*/}
            )
        }
    ) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(list) { carItem ->
                CarItemCard(
                    carItem = carItem,
                    onDeleteIconClick = { /* Handle delete */ },
                    onEditIconClick = { /* Handle edit */ }
                )
            }
        }
    }

    if (isDialogOpen) {
        Dialog(onDismissRequest = { isDialogOpen = false }) {
            CarItemDialog(
                onDismiss = { isDialogOpen = false },
                onSave = { name, description ->
                    // TODO: Handle the saved data (e.g., add to list)
                    isDialogOpen = false
                }
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    CarsScreen()
}