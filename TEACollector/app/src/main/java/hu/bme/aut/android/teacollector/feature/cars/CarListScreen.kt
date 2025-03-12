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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.teacollector.data.car.model.CarItem
import hu.bme.aut.android.teacollector.feature.cars.components.CarItemCard
import hu.bme.aut.android.teacollector.feature.cars.components.CarItemDialog
import hu.bme.aut.android.teacollector.feature.cars.components.CarListBottomBar
import hu.bme.aut.android.teacollector.feature.cars.components.CarListTopBar
//import hu.bme.aut.android.teacollector.feature.cars.components.filterCars


@Composable
fun CarListScreen(viewModel: CarsViewModel = viewModel(factory = CarsViewModel.Factory)) {

    val list = viewModel.carItemList.collectAsState().value

    var filteredCars by remember { mutableStateOf(list) }

    var isDialogOpen by remember { mutableStateOf(false) }
    var carToEdit by remember {mutableStateOf<CarItem?>(null)}

    val onSearchResults: (List<CarItem>) -> Unit = { searchResults ->
        filteredCars = searchResults
    }

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CarListTopBar(
                onMenuClick = {/*TODO*/ },
                onSearchQueryChange = { query ->
                    searchQuery = query
                    //onSearchResults(filterCars(query, list))
                },
                cars = list,
                onSearchResults = onSearchResults
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    carToEdit = null
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
            items(list, key = {item -> item.name}) { carItem ->
                CarItemCard(
                    carItem = carItem,
                    onDeleteIconClick = {
                        viewModel.delete(carItem)
                    },
                    onEditIconClick = {
                        carToEdit = it
                        isDialogOpen = true
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
                    // TODO: Handle the saved data (e.g., add to list)
                    if(carToEdit == null){
                        viewModel.insert(
                            CarItem(
                                name = name,
                                description = description,
                                isCollected = false,
                                lat = 0.0,
                                long = 0.0,
                                imageUri = imageUri?.toString()
                            )
                        )
                    }else{
                        viewModel.update(
                            carToEdit!!.copy(
                                name = name,
                                description = description
                            )
                        )
                    }
                    isDialogOpen = false
                }
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    CarListScreen()
}