package eu.example.kickstartrestaurants

/*
Functionally, this Use Case class gets the restaurants from
RestaurantsRepository, applies the business rule of sorting the restaurants
alphabetically, just like RestarauntsViewModel did, and then returns the
list. In other words, GetRestaurantsUseCase is now the one responsible for
applying business rules.
 */
class GetInitialRestaurantsUseCase {
	private val repository: RestaurantsRepository = RestaurantsRepository()
	private val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase()
	suspend operator fun invoke(): List<Restaurant>{
		repository.loadRestaurants()
		return getSortedRestaurantsUseCase()
	}
}