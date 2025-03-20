package hu.bme.aut.android.teacollector.data.car.model


data class CarItem(
    //var id: String = "",
    val name: String = "",         //TEA-123, license plate  value
    val imageUri: String? = "",     //URL to reach the image of teh car
    val isCollected: Boolean = false, //true if an image is connected to the record
    val description: String = "",   //any additional information
    val long: Double,         //longitude (east-west position on Earth)
    val lat: Double           //latitude (north-south position)
)