package hu.bme.aut.android.teacollector.screen.cars.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import hu.bme.aut.android.teacollector.data.car.model.CarItem
import hu.bme.aut.android.teacollector.navigation.Screen
import hu.bme.aut.android.teacollector.screen.cars.filterCars
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListTopBar(
    onSearchQueryChange: (String) -> Unit,
    cars: List<CarItem>,
    onSearchResults: (List<CarItem>) -> Unit,
){
    var searchQuery by remember {mutableStateOf("")}
    var dropdownExpanded by remember { mutableStateOf(false) };


    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ){
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {query ->
                        searchQuery = query
                        onSearchQueryChange(query)
                        onSearchResults(filterCars(query, cars))
                    },
                    placeholder = {
                        Text("Search...")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        },
        navigationIcon = {
            Box{
                IconButton(onClick = { dropdownExpanded = true }){
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menu Icon"
                    )
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = {dropdownExpanded = false}
                ){
                    DropdownMenuItem(
                        text = { Text("Collected")},
                        onClick = {
                            dropdownExpanded = false
                            val notCollectedCars = cars.filter { it.collected }
                            onSearchResults(notCollectedCars)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("To collect")},
                        onClick = {
                            dropdownExpanded = false
                            val CollectedCars = cars.filter { !it.collected }
                            onSearchResults(CollectedCars)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("All Cars") },
                        onClick = {
                            dropdownExpanded = false
                            onSearchResults(cars) // Show all cars
                        }
                    )
                }
            }

        }
    )
}
