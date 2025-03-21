package hu.bme.aut.android.teacollector.feature.cars.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.teacollector.R
import hu.bme.aut.android.teacollector.data.car.model.CarItem
import hu.bme.aut.android.teacollector.feature.cars.filterCars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListTopBar(
    onMenuClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    cars: List<CarItem>,
    onSearchResults: (List<CarItem>) -> Unit
){
    var searchQuery by remember {mutableStateOf("")}

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
            IconButton(onClick = onMenuClick){
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu Icon"
                )
            }
        }
    )
}

/*@Preview
@Composable
fun PreviewCarListTopBar() {
    CarListTopBar(onMenuClick = {}, onSearchQueryChange = {})
}*/