package hu.bme.aut.android.teacollector.data.car

import hu.bme.aut.android.teacollector.data.car.model.CarItem
import kotlinx.coroutines.flow.Flow

interface IShoppingItemRepository {

    fun getAllItems(): Flow<List<CarItem>>
    suspend fun insert(CarItem: CarItem)
    suspend fun update(CarItem: CarItem)
    suspend fun delete(CarItem: CarItem)
}