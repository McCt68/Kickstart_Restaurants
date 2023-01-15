package eu.example.kickstartrestaurants

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class RestaurantsRepository {

	private var restInterface: RestaurantsApiService =
		Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://holidaylist-8bd1c-default-rtdb.europe-west1.firebasedatabase.app/")
			.build()
			.create(RestaurantsApiService::class.java)

	private var restaurantsDao =
		RestaurantsDb
			.getDaoInstance(RestaurantsApplication.getAppContext())

	suspend fun toggleFavoriteRestaurant(
		id: Int,
		value: Boolean
	) =
		withContext(Dispatchers.IO) {
			restaurantsDao.update(
				PartialRestaurant(
					id = id,
					isFavorite = value)
			)
			// restaurantsDao.getAll()
		}

	//
	suspend fun loadRestaurants() {
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
			// return@withContext restaurantsDao.getAll()
		}
	}

	//
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

	//
	suspend fun getRestaurants(): List<Restaurant>{
		return withContext(Dispatchers.IO){
			return@withContext restaurantsDao.getAll()
		}
	}
}