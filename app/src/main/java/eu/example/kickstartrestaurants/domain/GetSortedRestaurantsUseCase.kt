package eu.example.kickstartrestaurants.domain

import eu.example.kickstartrestaurants.data.RestaurantsRepository

class GetSortedRestaurantsUseCase {

	private val repository: RestaurantsRepository =
		RestaurantsRepository()
	suspend operator fun invoke() : List<Restaurant>{
		return repository.getRestaurants().sortedBy {
			it.title
		}
	}
}