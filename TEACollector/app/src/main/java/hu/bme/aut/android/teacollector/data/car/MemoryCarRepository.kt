package hu.bme.aut.android.teacollector.data.car

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.teacollector.R
import hu.bme.aut.android.teacollector.data.car.model.CarItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MemoryCarRepository : ICarRepository {

    private var list = mutableStateListOf(
        CarItem(
            id = 1,
            name = "TEA-111",
            description = "szép rendszám",
            isCollected = true,
            long = 10.1,
            lat = 10.2
        ),
        CarItem(
            id = 2,
            name = "TEA-000",
            description = "szép rendszám",
            isCollected = true,
            long = 11.1,
            lat = 11.2
        )

    )

    override fun getAllItems(): Flow<List<CarItem>> = flow {
        emit(list)
    }

    override suspend fun insert(carItem: CarItem) {
        delay(1000)
        list.add(carItem.copy(id = (Long.MAX_VALUE*Math.random()).toLong()))
    }

    override suspend fun update(carItem: CarItem) {
        delay(1000)
        for (item in list) {
            if (item.id == carItem.id)
                list[list.indexOf(item)] = carItem
        }
    }

    override suspend fun delete(shoppingItem: CarItem) {
        delay(1000)
        list.remove(shoppingItem)
    }
}