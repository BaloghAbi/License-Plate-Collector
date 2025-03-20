package hu.bme.aut.android.teacollector.feature.carItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import hu.bme.aut.android.teacollector.feature.cars.CarsViewModel

@Composable
fun CarDetailScreen(
    carName: String,
    viewModel: CarsViewModel = viewModel(factory = CarsViewModel.Factory)
) {
    val car = viewModel.getCarByName(carName) ?: return
    var carName by remember { mutableStateOf(car?.name ?: "") }
    var carDescription by remember { mutableStateOf(car?.description) }

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
                    painter = rememberImagePainter(car.imageUri),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
            } else {
                    Button(onClick = {/*Handle upload*/ }) {
                        Text("Kép cseréje")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Car Name
            OutlinedTextField(
                value = carName,
                onValueChange = { carName = it },
                label = { Text("Autó neve") },
                modifier = Modifier.fillMaxWidth()
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