package eu.example.kickstartrestaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun RestaurantsScreen(
	state: RestaurantsScreenState,
	onItemClick: (id: Int) -> Unit = {},
	onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit
) {

	val viewModel: RestaurantsViewModel = viewModel()

	/*
	Observing the state from viewModel
	We are not changing the state in this view ever.
	We can trigger recomposition, by calling events in the viewModel
	that will change the state
	 */
	// val restaurantsStateFromViewModel = viewModel.state.value

	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier.fillMaxSize()
	) {
		LazyColumn(
			contentPadding = PaddingValues(
				vertical = 8.dp,
				horizontal = 8.dp
			)
		) {
			// items is a composable function that kinda takes a list, and do something with each item in the list
			items(state.restaurants) { restaurant ->
				RestaurantItem(
					restaurant,
					onFavoriteClick = { id, oldValue -> onFavoriteClick(id, oldValue)},
					onItemClick = { id -> onItemClick(id) }
				)
			}
		}
		// Show the Progress indicator when isLoading is true
		if (state.isLoading) CircularProgressIndicator()
		//
		if (state.error != null)
			Text(text = state.error)
	}


}

// Display one restaurant, we can then kinda loop over in lazyColumn
@Composable
fun RestaurantItem(
	item: Restaurant,
	onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit, // for setting favorite
	onItemClick: (id: Int) -> Unit // for navigation to detailScreen
) {
	val icon = if (item.isFavorite)
		Icons.Filled.Favorite
	else
		Icons.Filled.FavoriteBorder
	Card(
		elevation = 4.dp,
		modifier = Modifier
			.padding(8.dp)
			.clickable { onItemClick(item.id) }
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.padding(8.dp)
		) {
			RestaurantIcon(Icons.Filled.Place, Modifier.weight(0.15f))
			RestaurantDetails(item.title, item.description, Modifier.weight(0.7f))
			RestaurantIcon(icon, Modifier.weight(0.15f)) {
				onFavoriteClick(item.id, item.isFavorite)
			}
		}
	}
}

@Composable
fun RestaurantIcon(
	icon: ImageVector,
	modifier: Modifier,
	onClick: () -> Unit = { }
) {
	Image(
		imageVector = icon,
		contentDescription = "Restaurant icon",
		modifier = modifier
			.padding(8.dp)
			.clickable { onClick() })
}

// kinda info about 1 restaurant
@Composable
fun RestaurantDetails(
	title: String,
	description: String,
	modifier: Modifier,
	horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
	Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
		Text(
			text = title,
			style = MaterialTheme.typography.h6
		)
		CompositionLocalProvider(
			LocalContentAlpha provides ContentAlpha.medium
		) {
			Text(
				text = description,
				style = MaterialTheme.typography.body2
			)
		}
	}
}