package hu.bme.aut.android.teacollector.data.car

import hu.bme.aut.android.teacollector.data.car.model.CarItem
import kotlinx.coroutines.flow.Flow

interface ICarRepository {

    fun getAllItems(): Flow<List<CarItem>>
    suspend fun insert(carItem: CarItem)
    suspend fun update(carItem: CarItem)
    suspend fun delete(carItem: CarItem)
}