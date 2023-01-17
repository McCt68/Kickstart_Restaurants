package eu.example.kickstartrestaurants.domain

import eu.example.kickstartrestaurants.data.RestaurantsRepository

class ToggleRestaurantsUseCase {

	private val repository: RestaurantsRepository = RestaurantsRepository()

	private val getSortedRestaurantsUseCase =
		GetSortedRestaurantsUseCase()

	suspend operator fun invoke(id: Int, oldValue: Boolean): List<Restaurant>{
		val newFav = oldValue.not()
		repository.toggleFavoriteRestaurant(id, newFav)
		return getSortedRestaurantsUseCase()
	}
}