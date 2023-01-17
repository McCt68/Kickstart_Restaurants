package eu.example.kickstartrestaurants.domain

/*
Entities that define tables within the private database. In our Restaurants app, we
will consider the Restaurant data class as an entity. This means that we will have
a table populated with Restaurant objects. In other words, the rows of the table
are represented by instances of our restaurants.
We are kinda defining the rows with @ColumnInfo
 */
 // @Entity(tableName = "Restaurants") // Should maybe be restaurants instead ?? ( Not used here )
data class Restaurant(
	val id: Int,
	val title: String,
	val description: String,
	val isFavorite: Boolean = false
)

// A list of Restaurant objects
val dummyRestaurants = listOf(
	Restaurant(0, "Alfredo foods", "At Alfredo's you eat great"),
	Restaurant(1, "Burger King", "Better than Mac"),
	Restaurant(2, "Mc Donald's", "Worse than the King"),
	Restaurant(3, "Harry Pølser", "Pølse Mix"),
	Restaurant(4, "Kuk kak beach", "Thai Specials"),
	Restaurant(5, "Harry Pølser", "Pølse Mix"),
	Restaurant(6, "Harry Pølser", "Pølse Mix"),
	Restaurant(7, "Harry Pølser", "Pølse Mix"),
	Restaurant(8, "Mormor og Morfar", "Flæskesteg"),
	Restaurant(9, "Harry Pølser", "Pølse Mix"),
	Restaurant(10, "Harry Pølser", "Pølse Mix"),
	Restaurant(11, "Harry Pølser", "Pølse Mix"),
	Restaurant(12, "Harry Pølser", "Pølse Mix"),
	Restaurant(13, "Mor og Teddy", "Mmmhn det smager"),
)
