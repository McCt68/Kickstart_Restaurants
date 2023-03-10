package eu.example.kickstartrestaurants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
// import eu.example.kickstartrestaurants.RestaurantDetailsScreen
import eu.example.kickstartrestaurants.RestaurantsScreen
import eu.example.kickstartrestaurants.presentation.details.RestaurantDetailsScreen
import eu.example.kickstartrestaurants.presentation.list.RestaurantsScreenState
import eu.example.kickstartrestaurants.presentation.list.RestaurantsViewModel
import eu.example.kickstartrestaurants.ui.theme.KickstartRestaurantsTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			KickstartRestaurantsTheme {
				// my own path working with holidays, instead of restaurants
				// HolidayScreen()

				// Tutorial path, not working on this since chapter with viewModels
				//RestaurantsScreen()

				// RestaurantDetailsScreen()

				RestaurantsApp()
			}
		}
	}
}

@Composable
private fun RestaurantsApp() {

	// Handles navigation between composable screens
	val navController = rememberNavController() // Kinda remembers the state of navigation
	NavHost(navController = navController, startDestination = "restaurants") {

		// In the builder we define routes, and set corresponding @composable
		composable(route = "restaurants") {
			val viewModel: RestaurantsViewModel = viewModel()
			RestaurantsScreen(
				state = viewModel.state.value,
				onItemClick = { id ->
					navController.navigate("restaurants/$id")
				},
				onFavoriteClick = { id, oldValue ->
					viewModel.toggleFavorite(id, oldValue)
				})
		}

		composable(
			route = "restaurants/{restaurant_id}",
			arguments = listOf(navArgument("restaurant_id") {
				type = NavType.IntType
			}),
			deepLinks = listOf(navDeepLink {
				uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"
			})
		) {
			RestaurantDetailsScreen()
		}

	}
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	KickstartRestaurantsTheme {

		// my own path working wiht holidays, instead of restaturants
		// HolidayScreen()
		// Tutorial path, not working on this since chapter with viewModels
		RestaurantsScreen(
			RestaurantsScreenState(
				listOf(),
				true
			),
			{},
			{ _, _ -> }
		)
	}
}