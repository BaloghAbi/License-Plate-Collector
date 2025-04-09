package hu.bme.aut.android.teacollector.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.teacollector.data.auth.AuthService
import hu.bme.aut.android.teacollector.data.auth.FirebaseAuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class AuthViewModel(
    private val authService: AuthService = FirebaseAuthService()
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authService.login(email, password)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = result.isSuccess,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authService.register(email, password)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = result.isSuccess,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authService.logout()
            _uiState.update { it.copy(isSuccess = false) }
        }
    }

    fun getCurrentUserEmail(): String? {
        return authService.currentUserEmail
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel()
            }
        }
    }
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
