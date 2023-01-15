package eu.example.kickstartrestaurants

// Used to model the state of if the list is done loading

data class RestaurantsScreenState(
	val restaurants: List<Restaurant>,
	val isLoading: Boolean,
	val error: String? = null
)
