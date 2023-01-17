package eu.example.kickstartrestaurants.presentation.list

// import eu.example.kickstartrestaurants.Restaurant
import eu.example.kickstartrestaurants.domain.Restaurant

// Used to model the state of if the list is done loading

data class RestaurantsScreenState(
	val restaurants: List<Restaurant>,
	val isLoading: Boolean,
	val error: String? = null
)
