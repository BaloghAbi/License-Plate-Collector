package hu.bme.aut.android.teacollector.feature.cars.components

import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
//import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CarItemDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, Uri?) -> Unit
) {
    var name by remember { mutableStateOf("TEA-") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember {mutableStateOf<Uri?>(null)}

    val context = LocalContext.current

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()){ uri ->
            uri?.let{
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imageUri = it
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ){
            if(imageUri != null){
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Upload Image",
                    modifier = Modifier.fillMaxSize()
                )
            }else{
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply{
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                        putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.parse("content://media/external/images/tea"))
                    }
                    pickImageLauncher.launch(arrayOf("image/*"))
                }){
                    Text("Kép feltöltése")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

            //Car Name Field
        OutlinedTextField(
                value = name,
                onValueChange = { input ->
                    if (input.startsWith("TEA-")) {
                        // Get the numeric part after "TEA-", restrict it to 3 digits
                        val numericPart = input.substring(4).filter { it.isDigit() }.take(3)
                        // Update the name with the proper format "TEA-XXX"
                        name = "TEA-$numericPart"
                    } else {
                        // If the input doesn't start with "TEA-", we reset it to "TEA-" and allow only digits
                        name = "TEA-" + input.filter { it.isDigit() }.take(3)
                    }
                },
                label = { Text("Autó neve") },
                modifier = Modifier.fillMaxWidth(),
                    isError = name.length != 8 || !name.startsWith("TEA-")
        )

        Spacer(modifier = Modifier.height(16.dp))

            //Car Description Field
        OutlinedTextField(
                value = description,
                onValueChange = { description = it },
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
            Button(onClick = { onSave(name, description, imageUri) }) {
                    Text("Mentés") // Save Button
            }
        }
    }
}

@Preview
@Composable
fun PreviewCarItemDialog() {
    CarItemDialog(
        onDismiss = {},
        onSave = { _, _, _ -> }
    )
}