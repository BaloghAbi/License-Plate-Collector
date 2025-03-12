package hu.bme.aut.android.teacollector.feature.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.teacollector.teaCollectorApplication
import hu.bme.aut.android.teacollector.data.car.ICarRepository
import hu.bme.aut.android.teacollector.data.car.MemoryCarRepository
import hu.bme.aut.android.teacollector.data.car.model.CarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CarsViewModel(
    private val repository: ICarRepository
) : ViewModel() {
    private val _list = MutableStateFlow<List<CarItem>>(listOf())
    val carItemList = _list.asStateFlow()

    init {
        getAllItems()
    }

    fun getAllItems() {
        viewModelScope.launch {
            repository.getAllItems().collectLatest {
                _list.tryEmit(it)
            }
        }
    }

    fun insert(item: CarItem) {
        viewModelScope.launch {
            try {
                repository.insert(carItem = item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun update(item: CarItem) {
        viewModelScope.launch {
            try {
                repository.update(carItem = item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun delete(item: CarItem) {
        viewModelScope.launch {
            try {
                repository.delete(carItem = item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CarsViewModel(repository = teaCollectorApplication.repository)
            }
        }
    }
}