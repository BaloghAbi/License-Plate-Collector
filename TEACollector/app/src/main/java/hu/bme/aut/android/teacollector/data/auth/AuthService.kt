package hu.bme.aut.android.teacollector.data.auth

interface AuthService {
    val currentUserId: String?
    val currentUserEmail: String?
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun logout()
}


