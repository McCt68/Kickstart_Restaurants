package eu.example.kickstartrestaurants

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

// ViewModel for detail screen
class RestaurantDetailsViewModel(
	private val stateHandle: SavedStateHandle
): ViewModel() {

	private var restInterface: RestaurantsApiService

	// Holds a mutableState of nullable Restaurant objects with the initial value of null
	val detailsState = mutableStateOf<Restaurant?>(null)

	init {
		val retrofit: Retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory
				.create())
			.baseUrl("https://holidaylist-8bd1c-default-rtdb.europe-west1.firebasedatabase.app/")
			.build()
		restInterface = retrofit.create(
			RestaurantsApiService::class.java)

		val id = stateHandle.get<Int>("restaurant_id")?: 0 // elvis operator - set to zero if null

		viewModelScope.launch {
			val restaurant = getRemoteRestaurant(id = id)
			detailsState.value = restaurant
		}
	}

	// Get details of one restaurant by calling the method from restApiService on our restInterface object
	// the method is a private suspend function run on a separate I/O thread, and can only be called from within -
	// this scope since it is private.
	private suspend fun getRemoteRestaurant(id: Int): Restaurant{
		return withContext(Dispatchers.IO){
			val responseMap = restInterface
				.getRestaurant(id)
			return@withContext responseMap.values.first()
		}
	}
}