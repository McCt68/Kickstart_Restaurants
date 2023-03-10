package eu.example.kickstartrestaurants.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.example.kickstartrestaurants.RestaurantDetails
// import eu.example.kickstartrestaurants.RestaurantDetails
import eu.example.kickstartrestaurants.RestaurantDetailsViewModel
import eu.example.kickstartrestaurants.RestaurantIcon


@Composable
fun RestaurantDetailsScreen() {
	val viewModel: RestaurantDetailsViewModel = viewModel()
	val item = viewModel.detailsState.value
	if (item != null) {
		// call composables
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp)
		) {
			RestaurantIcon(
				icon = Icons.Filled.Place,
				modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
			)
			RestaurantDetails(
				title = item.title,
				description = item.description,
				modifier = Modifier.padding(bottom = 32.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			)
			Text(text = "More info coming soon")
		}
	}
}