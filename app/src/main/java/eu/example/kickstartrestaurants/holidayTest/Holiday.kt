package eu.example.kickstartrestaurants.holidayTest

import com.google.gson.annotations.SerializedName

data class Holiday(

	// kinda matching the kotlin var name to that of the json on the rest server (kinda)
	@SerializedName("r_id")
	val id: Int,
	@SerializedName("r.title")
	val title: String,
	@SerializedName("r_description")
	val description: String,
	var isFavorite: Boolean = false
)

// dummy list of holidays
val holidays = listOf(
	Holiday(0, "Barcelona", "Summer 2017"),
	Holiday(1, "Budapest", "December 2017"),
	Holiday(2, "Karon Beach - Khao Lak", "January 2018"),
	Holiday(3, "Barcelona", "Summer 2017"),
	Holiday(4, "Barcelona", "Summer 2017"),
	Holiday(5, "Barcelona", "Summer 2017"),
	Holiday(6, "Barcelona", "Summer 2017"),
	Holiday(7, "Barcelona", "Summer 2017"),
	Holiday(8, "Barcelona", "Summer 2017"),
	Holiday(9, "Barcelona", "Summer 2017"),
	Holiday(10, "Barcelona", "Summer 2017"),
	Holiday(11, "Barcelona", "Summer 2017"),
	Holiday(12, "Barcelona", "Summer 2017"),
)