package eu.example.kickstartrestaurants.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
RestaurantsDb is an abstract class that inherits from RoomDatabase().
This will allow Room to create the actual implementation of the database
behind the scenes and hide all the heavy implementation details from us.
For the RestaurantsDb class, we've added the @Database annotation
so that Room knows that this class represents a database and provides an
implementation for it. Inside this annotation, we've passed the following:
The Restaurant class to the entities parameter. This parameter tells
Room which entities are associated with this database so that it can create
corresponding tables. The parameter expects an array, so you can add as
many entity classes as you wish, as long as they are annotated with @Entity.
1 as the version number of the database. We should increment this
version number whenever the schema of the database changes. The schema
is the collection of database objects, such as the tables that correspond to
entities. If we change the Restaurant class, since it's an entity, we might
change the schema of the database, and Room needs to know that for
migration purposes.
false to the exportSchema parameter. Room can export the schema
 */

@Database(
	entities = [LocalRestaurant::class],
	version = 3, // was 1 when it worked before updating viewModel methods
	exportSchema = false
)
abstract class RestaurantsDb : RoomDatabase() {
	abstract val dao: RestaurantsDao


	// I think I can refer to this object without instantiating the class.
	/*
	Since we want only one memory reference to our database (and not create other
	database instances in other parts of the app), we made sure that our INSTANCE
	variable conforms to the singleton pattern. Essentially, the singleton pattern
	allows us to hold a static reference to an object so that it lives as long as
	our application does.
	 */
	companion object {
		@Volatile
		private var INSTANCE: RestaurantsDao? = null

		fun getDaoInstance(context: Context): RestaurantsDao {
			synchronized(this){
				var instance = INSTANCE
				if (instance == null){
					instance = buildDatabase(context).dao
					INSTANCE = instance
				}
				return instance
			}
		}

		// Returns a RestaurantsDb instance
		private fun buildDatabase(context: Context): RestaurantsDb =
			Room.databaseBuilder(
				context.applicationContext,
				RestaurantsDb::class.java,
				"restaurants_database")
				.fallbackToDestructiveMigration()
				.build()
	}
}