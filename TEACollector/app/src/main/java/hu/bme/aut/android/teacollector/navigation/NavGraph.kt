package hu.bme.aut.android.teacollector.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.teacollector.screen.auth.AuthViewModel
import hu.bme.aut.android.teacollector.screen.auth.LoginScreen
import hu.bme.aut.android.teacollector.screen.carItem.CarDetailScreen
import hu.bme.aut.android.teacollector.screen.cars.CarListScreen
import hu.bme.aut.android.teacollector.screen.map.MapScreen
import hu.bme.aut.android.teacollector.screen.profile.ProfileScreen
import hu.bme.aut.android.teacollector.screen.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val navigationHandler = NavigationHandler(navController)

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route){
            LoginScreen(onAuthSuccess = {
                navigationHandler.navigateToHome()
            })
        }

        composable(Screen.CarList.route) {
            CarListScreen(
                onCarItemClick = { carName ->
                    navigationHandler.navigateToCarDetail(carName)
                },
                navigationHandler = navigationHandler
            )
        }

        composable(
            route = Screen.CarDetail.route,
            arguments = listOf(navArgument("carName") { type = NavType.StringType })
        ) { backStackEntry ->
            val carName = backStackEntry.arguments?.getString("carName") ?: ""
            print("JUHU ran")
            CarDetailScreen(carName = carName)
        }

        composable(Screen.MainMap.route) {
            MapScreen(navigationHandler)
        }

        composable(Screen.Profile.route) {
            val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
            ProfileScreen(
                navigationHandler = navigationHandler,
                viewModel = authViewModel
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(navigationHandler)
        }
    }
}
