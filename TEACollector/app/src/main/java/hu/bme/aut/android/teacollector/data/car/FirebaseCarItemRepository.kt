package hu.bme.aut.android.teacollector.data.car.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import hu.bme.aut.android.teacollector.data.car.ICarRepository
import hu.bme.aut.android.teacollector.data.car.model.CarItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirebaseCarItemRepository : ICarRepository {

    private val db = FirebaseFirestore.getInstance()
    private val carsCollection = db.collection("cars")

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fillDatabase() // Runs in background when repository is initialized
        }
    }

    private suspend fun fillDatabase() {
        for (i in 0 until 1000) {
            val carName = "TEA-${i.toString().padStart(3, '0')}"
            val carDocRef = carsCollection.document(carName)

            try {
                val carDoc = carDocRef.get().await()

                if (!carDoc.exists()) {
                    val carItem = mapOf(
                        "name" to carName,
                        "description" to "",
                        "isCollected" to false,
                        "lat" to 0.0,
                        "long" to 0.0
                    )
                    carDocRef.set(carItem).await()
                    println("Inserted: $carName")
                }
            } catch (e: Exception) {
                println("Error seeding database: ${e.message}")
            }
        }
    }

    override fun getAllItems(): Flow<List<CarItem>> = callbackFlow {
        val listener = carsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val cars = snapshot.documents.mapNotNull { doc ->
                    val car = doc.toObject<FirebaseCarItem>()?.asCarItem()
                    println("Loaded Car: ${car?.name}, Collected: ${car?.isCollected}")
                    car
                }
                trySend(cars).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }


    override suspend fun insert(carItem: CarItem) {
        val firebaseCar = carItem.asFirebaseCarItem()
        carsCollection.document(firebaseCar.name).set(firebaseCar).await()
    }

    override suspend fun update(carItem: CarItem) {
        val firebaseCar = carItem.asFirebaseCarItem()
        carsCollection.document(firebaseCar.name).set(firebaseCar).await()
    }

    override suspend fun delete(carItem: CarItem) {
        carsCollection.document(carItem.name).delete().await()
    }
}
