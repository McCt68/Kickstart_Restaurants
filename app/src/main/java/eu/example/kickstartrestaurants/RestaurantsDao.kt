package eu.example.kickstartrestaurants

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/*
Since Room will take care of implementing any actions that we need to interact
with the database, the DAO is an interface, just like Retrofit also had an interface
for the HTTP methods. To instruct Room that this is a DAO entity, we've added the
@Dao annotation on top of the interface declaration.
 */
@Dao
interface RestaurantsDao {

	/*
	getAll() is a query statement that returns the restaurants that were
	previously cached inside the database. Since we need to perform a SQL query
	when calling this method, we've marked it with the @Query annotation
	and specified that we want all the restaurants (by adding *) from the
	restaurants table defined in the Restaurant entity data class.
	 */
	@Query("SELECT * FROM restaurants")
	suspend fun getAll(): List<Restaurant>


	/*
	addAll() is an insert statement that caches the received restaurants
	inside the database. To mark this as a SQL insert statement, we've added
	the @Insert annotation. However, if the restaurants being inserted are
	already present in the database, we should replace the old ones with the
	new ones to refresh our cache. We instructed Room to do so by passing the
	OnConflictStrategy.REPLACE value into the @Insert annotation.
	 */
	// kinda saving into room db
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun addAll(restaurants: List<Restaurant>)

	@Update(entity = Restaurant::class)
	suspend fun update(partialRestaurant: PartialRestaurant)

	@Update(entity = Restaurant::class)
	suspend fun updateAll(partialRestaurants: List<PartialRestaurant>)

	@Query("SELECT * FROM restaurants WHERE is_favorite = 1")
	suspend fun getAllFavorited(): List<Restaurant>
}