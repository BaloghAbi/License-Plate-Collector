package hu.bme.aut.android.teacollector.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.teacollector.feature.carItem.CarDetailScreen
import hu.bme.aut.android.teacollector.feature.cars.CarListScreen
import hu.bme.aut.android.teacollector.feature.map.MapScreen
import hu.bme.aut.android.teacollector.feature.profile.ProfileScreen
import hu.bme.aut.android.teacollector.feature.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
){
    NavHost(
        navController = navController,
        startDestination = Screen.CarList.route
    ){
        //Car list
        composable(Screen.CarList.route){
            CarListScreen(
                onCarItemClick = {carName ->
                    navController.navigate(Screen.CarDetail.createRoute(carName))
                },
                navController = navController
            )
        }

        //Car details
        composable(Screen.CarDetail.route,
            arguments = listOf(
                navArgument("carName"){type = NavType.StringType}
            )
        ) { backStackEntry ->
            val carName = backStackEntry.arguments?.getString("carName") ?: ""
            CarDetailScreen(carName = carName)
        }

        // Map screen
        composable(Screen.MainMap.route) {
            MapScreen(navController = navController)
        }

        //Profile screen
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        //Settings screen
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}