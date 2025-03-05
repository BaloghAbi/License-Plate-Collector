package hu.bme.aut.android.teacollector.feature.cars.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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

@Composable
fun CarItemDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var carName by remember { mutableStateOf("") }
    var carDescription by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Image Upload Button
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text("Kép feltöltése")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Car Name Field
            OutlinedTextField(
                value = carName,
                onValueChange = { carName = it },
                label = { Text("Autó neve") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Car Description Field
            OutlinedTextField(
                value = carDescription,
                onValueChange = { carDescription = it },
                label = { Text("Leírás") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Buttons (Save&Cancel)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onDismiss() }) {
                    Text("Mégse") // Cancel Button
                }
                Button(onClick = { onSave(carName, carDescription) }) {
                    Text("Mentés") // Save Button
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCarItemDialog() {
    CarItemDialog(
        onDismiss = {},
        onSave = { _, _ -> }
    )
}