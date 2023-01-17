package eu.example.kickstartrestaurants.data.remote

import com.google.gson.annotations.SerializedName

/*  DTO
Contains the fields received from the Web API.
These fields will also be annotated with GSON serialization
annotations required by Retrofit to parse the response.
 */
data class RemoteRestaurant(
	@SerializedName("r_id")
	val id: Int,
	@SerializedName("r_title")
	val title: String,
	@SerializedName("r_description")
	val description: String,
)
