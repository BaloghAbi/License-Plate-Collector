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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class MemoryCarRepository : ICarRepository {

    private val _carItems = MutableStateFlow<List<CarItem>>(listOf(
        *Array(1000) { i ->
            CarItem(
                name = "TEA-${i.toString().padStart(3, '0')}",
                description = "",
                isCollected = false,
                lat = 0.0,
                long = 0.0
            )
        }
    ))

    override fun getAllItems(): Flow<List<CarItem>> = _carItems

    override suspend fun insert(carItem: CarItem) {
        delay(1000)
        //_carItems.value = _carItems.value + carItem.copy(id = (Long.MAX_VALUE * Math.random()).toLong().toString())
    }

    override suspend fun update(carItem: CarItem) {
        delay(1000)
        _carItems.value = _carItems.value.map { toUpdateCar ->
            if (toUpdateCar.name == carItem.name) carItem else toUpdateCar
        }
    }

    override suspend fun delete(carItem: CarItem) {
        delay(1000)
        _carItems.value = _carItems.value.filterNot { it.name == carItem.name }
    }
}