package hu.bme.aut.android.teacollector.data.car.model


data class CarItem(
    //Good to know: In Java Beans an is prefix on a boolean field/method indicates a boolean property, ignores "is". isCollected is mapped to a JSON property with the name collected (name in fireBase)
    val name: String = "",         //TEA-123, license plate  value
    val imageUri: String? = "",     //URL to reach the image of teh car
    val collected: Boolean = false, //true if an image is connected to the record
    val description: String = "",   //any additional information
    val long: Double,         //longitude (east-west position on Earth)
    val lat: Double,           //latitude (north-south position)
    //val collectors: List<String> = listOf() //user IDs who  collected it
){
    constructor() : this("", "", false, "", 0.0, 0.0)
}