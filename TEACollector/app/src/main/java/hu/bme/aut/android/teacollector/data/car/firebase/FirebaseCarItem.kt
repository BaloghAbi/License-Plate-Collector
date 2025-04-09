package hu.bme.aut.android.teacollector.data.car.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.teacollector.data.car.model.CarItem
/*
data class FirebaseCarItem(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val collected: Boolean = false,
    val lat: Double = 0.0,
    val long: Double = 0.0
)

fun FirebaseCarItem.toCarItem(): CarItem = CarItem(
    name = name,
    description = description,
    collected = collected,
    lat = lat,
    long = long
)

fun CarItem.toFirebaseCarItem(): FirebaseCarItem = FirebaseCarItem(
    name = name,
    description = description,
    collected = collected,
    lat = lat,
    long = long
)
*/