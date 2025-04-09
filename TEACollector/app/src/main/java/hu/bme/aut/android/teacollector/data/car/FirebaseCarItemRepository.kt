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
        //originally the ceil value must be 1000
        for (i in 0 until 10) {
            val carName = "TEA-${i.toString().padStart(3, '0')}"
            val carDocRef = carsCollection.document(carName)

            try {
                val carDoc = carDocRef.get().await()

                if (!carDoc.exists()) {
                    val carItem = mapOf(
                        "name" to carName,
                        "description" to "",
                        "collected" to false,
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
                    val car = doc.toObject<CarItem>()
                    println("Loaded Car: ${car?.name}, Collected: ${car?.collected}")
                    car
                }
                trySend(cars).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }


    override suspend fun insert(carItem: CarItem) {
        carsCollection.document(carItem.name).set(carItem).await()
    }

    //solved the problem of serialization (isConnected was stores as "collected")
    override suspend fun update(carItem: CarItem) {
        carsCollection.document(carItem.name).set(carItem).await()
    }

    override suspend fun delete(carItem: CarItem) {
        carsCollection.document(carItem.name).delete().await()
    }
}

/*something like that when auth service added
class FirebaseCarItemRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val authService: AuthService = AuthService()
) : ICarRepository {

    private fun currentUserCarCollection(): CollectionReference {
        val uid = authService.currentUserId
            ?: throw IllegalStateException("User must be authenticated")
        return firestore.collection("users").document(uid).collection("cars")
    }

    override fun getAllItems(): Flow<List<CarItem>> = callbackFlow {
        val listener = currentUserCarCollection().addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val cars = snapshot.documents.mapNotNull { it.toObject<CarItem>() }
                trySend(cars).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun insert(carItem: CarItem) {
        currentUserCarCollection().document(carItem.name).set(carItem).await()
    }

    override suspend fun update(carItem: CarItem) {
        currentUserCarCollection().document(carItem.name).set(carItem).await()
    }

    override suspend fun delete(carItem: CarItem) {
        currentUserCarCollection().document(carItem.name).delete().await()
    }

    // Optional: Seeding logic — only if needed per user
    suspend fun fillDatabase() {
        for (i in 0 until 10) {
            val carName = "TEA-${i.toString().padStart(3, '0')}"
            val carDocRef = currentUserCarCollection().document(carName)
            try {
                val carDoc = carDocRef.get().await()
                if (!carDoc.exists()) {
                    val carItem = CarItem(
                        name = carName,
                        description = "",
                        collected = false,
                        lat = 0.0,
                        long = 0.0
                    )
                    carDocRef.set(carItem).await()
                }
            } catch (e: Exception) {
                println("Error seeding database: ${e.message}")
            }
        }
    }
}
*/
