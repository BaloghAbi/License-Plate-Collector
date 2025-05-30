package hu.bme.aut.android.teacollector.screen.cars

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import hu.bme.aut.android.teacollector.data.car.model.CarItem
import hu.bme.aut.android.teacollector.navigation.NavigationHandler
import hu.bme.aut.android.teacollector.screen.cars.components.CarItemCard
import hu.bme.aut.android.teacollector.screen.cars.components.CarItemDialog
import hu.bme.aut.android.teacollector.screen.cars.components.CarListBottomBar
import hu.bme.aut.android.teacollector.screen.cars.components.CarListTopBar
import hu.bme.aut.android.teacollector.navigation.Screen
import hu.bme.aut.android.teacollector.ui.theme.TEAGreen

//import hu.bme.aut.android.teacollector.feature.cars.components.filterCars


@Composable
fun CarListScreen(
    viewModel: CarsViewModel = viewModel(factory = CarsViewModel.Factory),
    onCarItemClick: (String) -> Unit, //pass car id to navigate
    navigationHandler: NavigationHandler
    ) {

    /*val list = viewModel.carItemList.collectAsState().value
    var filteredCars by remember { mutableStateOf(list) }*/

    //added to load updated screen
    val list by viewModel.carItemList.collectAsState()
    var filteredCars by remember { mutableStateOf(list) }
    LaunchedEffect(list) {
        filteredCars = list
    }


    var isDialogOpen by remember { mutableStateOf(false) }
    var carToEdit by remember {mutableStateOf<CarItem?>(null)}

    val onSearchResults: (List<CarItem>) -> Unit = { searchResults ->
        filteredCars = searchResults
    }

    Scaffold(
        topBar = {
            CarListTopBar(
                onSearchQueryChange = { query ->
                    filteredCars = list.filter{
                        it.name.contains(query, ignoreCase = true)
                    }
                },
                cars = list,
                onSearchResults = { results ->
                    filteredCars = results
                }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                containerColor = TEAGreen,
                onClick = {
                    carToEdit = null
                    isDialogOpen = true
                }
                ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new item"
                )
            }
        },
        bottomBar = {
            CarListBottomBar(navigationHandler)
        }
    ) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(filteredCars, key = {item -> item.name}) { carItem ->
                CarItemCard(
                    carItem = carItem,
                    onCardClick = { carName ->
                        carName?.let {
                            onCarItemClick(it)
                        }
                    }
                )
            }
        }
    }

    if (isDialogOpen) {
        Dialog(onDismissRequest = { isDialogOpen = false }) {
            CarItemDialog(
                onDismiss = { isDialogOpen = false },
                onSave = { name, description, imageUri ->
                    val toUpdateCar = list.find { it.name == name }
                    if(toUpdateCar != null){
                        viewModel.update(
                            toUpdateCar.copy(
                                description = description,
                                imageUri = imageUri?.toString(),
                                collected = true
                            )
                        )
                    }
                    isDialogOpen = false
                }
            )
        }
    }
}

fun filterCars(query: String, cars: List<CarItem>): List<CarItem> {
    val normalizedQuery = query.trim().uppercase()

    return cars.filter { car ->
        when {
            // Matches "TEA-123"
            normalizedQuery.startsWith("TEA-") && normalizedQuery.length == 7 ->
                car.name.uppercase().startsWith(normalizedQuery)

            // Matches last 3 digits like "123"
            normalizedQuery.length == 3 && normalizedQuery.all { it.isDigit() } ->
                car.name.takeLast(3) == normalizedQuery

            else -> false
        }
    }
}
