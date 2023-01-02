package eu.example.kickstartrestaurants

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException


class RestaurantsViewModel() : ViewModel() {
	private var restInterface: RestaurantsApiService
	private var restaurantsDao =
		RestaurantsDb.getDaoInstance(RestaurantsApplication.getAppContext())

	val state = mutableStateOf(emptyList<Restaurant>())

	private val errorHandler = CoroutineExceptionHandler { _, exception ->
		exception.printStackTrace()
	}

	init {
		val retrofit: Retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://holidaylist-8bd1c-default-rtdb.europe-west1.firebasedatabase.app/")
			.build()
		restInterface = retrofit.create(RestaurantsApiService::class.java)
		getRestaurants()
	}

	private suspend fun toggleFavoriteRestaurant(
		id: Int,
		oldValue: Boolean
	) =
		withContext(Dispatchers.IO) {
			restaurantsDao.update(
				PartialRestaurant(
					id = id,
					isFavorite = !oldValue
				)
			)
			restaurantsDao.getAll()
		}

	fun toggleFavorite(id: Int, oldValue: Boolean) {
		viewModelScope.launch(errorHandler) {
			val updatedRestaurants =
			toggleFavoriteRestaurant(id, oldValue)
			state.value = updatedRestaurants
		}
	}

//	private fun storeSelection(item: Restaurant) {
//		val savedToggled = stateHandle.get<List<Int>?>(FAVORITES)
//			.orEmpty().toMutableList()
//		if (item.isFavorite) savedToggled.add(item.id)
//		else savedToggled.remove(item.id)
//		stateHandle[FAVORITES] = savedToggled
//	}

	private suspend fun getAllRestaurants():
			List<Restaurant> {
		return withContext(Dispatchers.IO) {
			try {
				refreshCache()
			} catch (e: Exception) {
				when (e) {
					is UnknownHostException,
					is ConnectException,
					is HttpException -> {
						if (restaurantsDao.getAll().isEmpty())
							throw Exception(
								"Something went wrong. " +
										"We have no data."
							)
					}
					else -> throw e
				}
			}
			return@withContext restaurantsDao.getAll()
		}
	}

	private suspend fun refreshCache() {
		val remoteRestaurants = restInterface
			.getRestaurants() // get list of restaurants from firestore - all favorites are set to false
		val favoriteRestaurants = restaurantsDao
			.getAllFavorited() // get list of Favorited restaurants from room -
		restaurantsDao.addAll(remoteRestaurants) // now overwritting room (local) favorited to false
		// get the partial list of favorited restaurants, and overwrite room with those
		restaurantsDao.updateAll(favoriteRestaurants.map {
			PartialRestaurant(it.id, true)
		})
	}

	private fun getRestaurants() {
		// Run in Dispatchers.IO
		viewModelScope.launch(errorHandler) {
			state.value = getAllRestaurants()
		}
	}

	// Extension function
//	private fun List<Restaurant>.restoreSelections(): List<Restaurant> {
//		stateHandle.get<List<Int>?>(FAVORITES)?.let { selectedIds ->
//			val restaurantsMap = this.associateBy { it.id }
//				.toMutableMap()
//			selectedIds.forEach { id ->
//				val restaurant = restaurantsMap[id] ?: return@forEach
//				restaurantsMap[id] =
//					restaurant.copy(isFavorite = true)
//			}
//			return restaurantsMap.values.toList()
//		}
//		return this
//	}

//	companion object {
//		const val FAVORITES = "favorites"
//	}
}