package eu.example.kickstartrestaurants

import retrofit2.http.GET
import retrofit2.http.Query

// I think here I am using kinda a retrofit language with annotated style -
// and then also kinda translate that same function to kotlin language

interface RestaurantsApiService {

	// Kinda add this path to .baseUrl
	@GET("restaurants.json") // Execute a GET HTTP action to this path (kinda)
	suspend fun getRestaurants(): List<Restaurant>

	// Get details of one restaurant
	// It returns a Map where the key is a string, and the value is a Restaurant
	@GET("restaurants.json?orderBy=\"r_id\"")
	suspend fun getRestaurant(
		@Query("equalTo") id:Int
	): Map<String, Restaurant>
}