package hu.bme.aut.android.teacollector.screen.cars.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hu.bme.aut.android.teacollector.R
import hu.bme.aut.android.teacollector.data.car.model.CarItem

@Composable
fun CarItemCard(
    carItem: CarItem,
    onCardClick: (String) -> Unit //pass carName
) {
    var isChecked by rememberSaveable { mutableStateOf(carItem.isCollected) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick(carItem.name) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(carItem.imageUri != null){
                AsyncImage(
                    model = carItem.imageUri.takeIf {!it.isNullOrEmpty()}, // Fallback to empty string to avoid null issues
                    contentDescription = "Car Image",
                    modifier = Modifier.size(80.dp)
                )
            }else {
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(R.drawable.placeholder), // Placeholder image
                    contentDescription = "Car Image"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = carItem.name, fontSize = 20.sp, maxLines = 1)
                Text(text = carItem.description, maxLines = 2)
            }
        }
    }
}


/*@Preview
@Composable
fun PreviewCarItemCard() {
    CarItemCard(
        carItem = CarItem(
            name = "TEA-123",
            description = "Szép rendszám",
            long = 10.1,
            lat = 10.2
        )
    )
}*/