package hu.bme.aut.android.teacollector.screen.carItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import hu.bme.aut.android.teacollector.screen.cars.CarsViewModel

@Composable
fun CarDetailScreen(
    carName: String,
    viewModel: CarsViewModel = viewModel(factory = CarsViewModel.Factory)
) {
    val carList by viewModel.carItemList.collectAsState() //observe the list
    //val car = remember { viewModel.getCarByName(carName) }
    val car = carList.find { it.name == carName }

    var carName by remember { mutableStateOf("") }
    var carDescription by remember { mutableStateOf("") }

    LaunchedEffect(car) {
        car?.let {
            carName = it.name
            carDescription = it.description ?: ""
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Image Upload Button
        Box(
            modifier = Modifier
                .size(400.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (car?.imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(car.imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(), // fills the whole box
                    contentScale = ContentScale.Crop // or .Fit, .FillBounds depending on the effect
                )
            } else {
                    Button(onClick = {/*Handle upload*/ }) {
                        Text("Kép cseréje")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Car Name
            Text(
                text = carName
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Car Description
            OutlinedTextField(
                value = carDescription ?: "", //default value
                onValueChange = { carDescription = it },
                label = { Text("Leírás") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Map Placeholder (Green Box)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Green, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Térkép helye", color = Color.White)
            }
        }
}

@Preview
@Composable
fun PreviewCarItemScreen() {
    CarDetailScreen(carName = "TEA-123")
}