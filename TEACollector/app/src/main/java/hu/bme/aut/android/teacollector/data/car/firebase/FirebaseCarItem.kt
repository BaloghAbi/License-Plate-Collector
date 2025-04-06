package hu.bme.aut.android.teacollector.data.car.firebase

import hu.bme.aut.android.teacollector.data.car.model.CarItem

// firebase model for CarItem
data class FirebaseCarItem(
    val name: String = "",       //License plate (TEA-123)
    val imageUri: String? = "",  //URL to car image
    val collected: Boolean = false, // true if an image is connected
    val description: String = "", // descrip.
    val long: Double = 0.0,       // Longitude
    val lat: Double = 0.0         // Latitude
) {
    // Empty constructor
    constructor() : this("", "", false, "", 0.0, 0.0)
}

//convert FirebaseCarItem to CarItem
fun FirebaseCarItem.asCarItem() = CarItem(
    name = name,
    imageUri = imageUri,
    isCollected = collected,
    description = description,
    long = long,
    lat = lat
)

// Convert CarItem to FirebaseCarItem
fun CarItem.asFirebaseCarItem() = FirebaseCarItem(
    name = name,
    imageUri = imageUri,
    collected = isCollected,
    description = description,
    long = long,
    lat = lat
)
