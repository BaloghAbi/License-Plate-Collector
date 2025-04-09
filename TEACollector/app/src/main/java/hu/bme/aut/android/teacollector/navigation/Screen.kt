package hu.bme.aut.android.teacollector.navigation

sealed class Screen(val route: String) {
    object CarList: Screen("car_list_mainscreen")
    object CarDetail: Screen("carDetail/{carName}"){
        fun createRoute(carName: String) = "carDetail/$carName"
    }
    object MainMap: Screen("carMapScreen")
    object Profile: Screen("profile")
    object Settings: Screen("settings")

    object Login: Screen("login")
}