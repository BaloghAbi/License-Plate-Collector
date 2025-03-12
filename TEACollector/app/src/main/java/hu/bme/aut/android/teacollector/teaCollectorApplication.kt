package hu.bme.aut.android.teacollector

import android.app.Application
import hu.bme.aut.android.teacollector.data.car.MemoryCarRepository
import hu.bme.aut.android.teacollector.data.car.ICarRepository

class teaCollectorApplication : Application() {

    companion object {
        lateinit var repository: ICarRepository
    }

    override fun onCreate() {
        super.onCreate()
        repository = MemoryCarRepository()
    }
}