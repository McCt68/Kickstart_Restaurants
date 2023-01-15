package eu.example.kickstartrestaurants

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*


class RestaurantsViewModel() : ViewModel() {

	// Need to uncomment this ?
	// private val repository = RestaurantsRepository()

	private val getRestaurantsUseCase =
		GetInitialRestaurantsUseCase()

	private val toggleRestaurantsUseCase =
		ToggleRestaurantsUseCase()

	/*
	Encapsulation -
	The _state is a private mutableStateOf the data class ResturantsScreenState -
	that consist of a list of restaurants, and a boolean value that indicates either the list is loading or not.
	the _state is private so it can only be used , and changed inside this class.
	 */
	private val _state = mutableStateOf(
		RestaurantsScreenState(
			restaurants = listOf(),
			isLoading = true
		)
	)

	// we expose this to the view, that observes this viewModel
	val state: State<RestaurantsScreenState>
		get() = _state // returns the content from the _state variable, to who ever is observing this

	private val errorHandler = CoroutineExceptionHandler { _, exception ->
		exception.printStackTrace()
		_state.value = _state.value.copy(
			error = exception.message,
			isLoading = false
		)
	}

	init {
		getRestaurants() // get restaurants and thereby update the state
	}

	fun toggleFavorite(
		id: Int,
		oldValue: Boolean
	) {
		viewModelScope.launch(errorHandler) {
			val updatedRestaurants = toggleRestaurantsUseCase(id, oldValue)
			// update state - trigger recompositon when state changes value
			_state.value = _state.value.copy(restaurants = updatedRestaurants)
		}
	}

	private fun getRestaurants() {
		viewModelScope.launch(errorHandler) {
			val restaurants = getRestaurantsUseCase()
			// update state
			_state.value = _state.value.copy(
				restaurants = restaurants,
				isLoading = false // set isLoading to false, and thereby stop the progress indicator in the view
			)
		}
	}
}