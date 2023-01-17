package eu.example.kickstartrestaurants.data

import eu.example.kickstartrestaurants.RestaurantsApplication
import eu.example.kickstartrestaurants.data.local.LocalRestaurant
import eu.example.kickstartrestaurants.data.local.PartialLocalRestaurant
import eu.example.kickstartrestaurants.data.local.RestaurantsDb
import eu.example.kickstartrestaurants.data.remote.RestaurantsApiService
import eu.example.kickstartrestaurants.domain.Restaurant
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
				PartialLocalRestaurant(
					id = id,
					isFavorite = value)
			)
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
		restaurantsDao.addAll(remoteRestaurants.map {
			LocalRestaurant(
				it.id, it.title, it.description, false
			)
		}) // now overwritting room (local) favorited to false
		// get the partial list of favorited restaurants, and overwrite room with those
		restaurantsDao.updateAll(favoriteRestaurants.map {
			PartialLocalRestaurant(it.id, true)
		})
	}

	//
	suspend fun getRestaurants(): List<Restaurant>{
		return withContext(Dispatchers.IO){
			return@withContext restaurantsDao.getAll().map {
				Restaurant(it.id, it.title, it.description, it.isFavorite)
			}
		}
	}
}