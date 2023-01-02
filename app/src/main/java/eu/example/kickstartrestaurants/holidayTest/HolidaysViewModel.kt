package eu.example.kickstartrestaurants.holidayTest

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

// ENTIRE CODE IS COMMENTED OUT FOR TESTING WITH RESTAURANTS URL

//
//class HolidaysViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
//
//	private var restInterface: RestaurantsApiService
//
//	// state we can observe from the view ( holidayScreen )
//	// its an empty list of Holiday data objects
//	// We will populate the list when we use the getRestaurants function -
//	// thereby getting the list from firebase
//	val state = mutableStateOf(emptyList<Holiday>())
//
//	private lateinit var restaurantsCall: Call<List<Holiday>>
//
//	init {
//		val retrofit: Retrofit = Retrofit.Builder()
//			.addConverterFactory(GsonConverterFactory.create())
//			//.baseUrl("https://restaurants-db-default-rtdb.firebaseio.com") // replace with my own path
//			.baseUrl("https://holidaylist-8bd1c-default-rtdb.europe-west1.firebasedatabase.app/") // test
//			.build()
//		restInterface = retrofit.create(RestaurantsApiService::class.java)
//		getRestaurants()
//	}
//
//	private fun getRestaurants() {
//		restaurantsCall = restInterface.getRestaurants()
//		restaurantsCall.enqueue(
//			object : Callback<List<Holiday>> {
//				override fun onResponse(
//					call: Call<List<Holiday>>,
//					response: Response<List<Holiday>>
//				) {
//					response.body()?.let { holidays ->
//						state.value = holidays.restoreSelections()
//					}
//				}
//
//				override fun onFailure(
//					call: Call<List<Holiday>>,
//					t: Throwable
//				) {
//					t.printStackTrace()
//				}
//			}
//		)
//	}
//
//	// Event we can call from the view to change the state
//	fun toggleFavorite(id: Int) {
//		val holidaysToggle = state.value.toMutableList()
//		val itemIndex = holidaysToggle.indexOfFirst { it.id == id }
//		val item = holidaysToggle[itemIndex]
//		holidaysToggle[itemIndex] = item.copy(isFavorite = !item.isFavorite)
//		storeSelection(holidaysToggle[itemIndex])
//		state.value = holidaysToggle
//	}
//
//	private fun storeSelection(item: Holiday) {
//		val savedToggled = stateHandle
//			.get<List<Int>?>(FAVORITES)
//			.orEmpty().toMutableList()
//		if (item.isFavorite) savedToggled.add(item.id)
//		else savedToggled.remove(item.id)
//		stateHandle[FAVORITES] = savedToggled
//	}
//
//	companion object {
//		const val FAVORITES = "favorites"
//	}
//
//	// Extension function to list ??
//	private fun List<Holiday>.restoreSelections(): List<Holiday> {
//		stateHandle.get<List<Int>?>(FAVORITES)?.let { selectedIds ->
//			val holidaysMap = this.associateBy { it.id }
//			selectedIds.forEach { id ->
//				holidaysMap[id]?.isFavorite = true
//			}
//			return holidaysMap.values.toList()
//		}
//		return this
//	}
//
//	// is this right ??
//	override fun onCleared() {
//		super.onCleared()
//		restaurantsCall.cancel()
//	}
//}
//
