package hu.bme.aut.android.teacollector.navigation

import androidx.navigation.NavController

class NavigationHandler(private val navController: NavController) {
    fun navigateToCarDetail(carName: String) {
        navController.navigate(Screen.CarDetail.createRoute(carName))
    }

    fun navigateToMap() {
        navController.navigate(Screen.MainMap.route)
    }

    fun navigateToProfile() {
        navController.navigate(Screen.Profile.route)
    }

    fun navigateToHome() {
        navController.navigate(Screen.CarList.route)
    }

    fun navigateToSettings() {
        navController.navigate(Screen.Settings.route)
    }

    fun navigateToLogin(){
        navController.navigate(Screen.Login.route)
    }

    fun goBack() {
        navController.popBackStack()
    }
}
