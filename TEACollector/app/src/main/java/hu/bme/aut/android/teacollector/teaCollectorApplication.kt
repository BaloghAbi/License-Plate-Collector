package hu.bme.aut.android.teacollector

import android.app.Application
import com.google.firebase.FirebaseApp
import hu.bme.aut.android.teacollector.data.car.MemoryCarRepository
import hu.bme.aut.android.teacollector.data.car.ICarRepository
import hu.bme.aut.android.teacollector.data.car.firebase.FirebaseCarItemRepository

class teaCollectorApplication : Application() {

    companion object {
        lateinit var repository: ICarRepository
    }

    override fun onCreate() {
        super.onCreate()
        //repository = MemoryCarRepository()
        try {
            FirebaseApp.initializeApp(this)
            println("Successful initialization")
        } catch (e: Exception) {
            println("Firebase initialization failed: ${e.message}")
        }
        repository = FirebaseCarItemRepository()
    }
}