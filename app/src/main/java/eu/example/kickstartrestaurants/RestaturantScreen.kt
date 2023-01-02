package eu.example.kickstartrestaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
fun RestaurantsScreen(onItemClick: (id: Int) -> Unit = {}) {
	val viewModel: RestaurantsViewModel = viewModel()
	LazyColumn(
		contentPadding = PaddingValues(
			vertical = 8.dp,
			horizontal = 8.dp
		)
	) {
		// items is a composable function that kinda takes a list, and do something with each item in the list
		items(viewModel.state.value) { restaurant ->
			RestaurantItem(
				restaurant,
				onFavoriteClick = { id, oldValue -> viewModel.toggleFavorite(id, oldValue) },
				onItemClick = { id -> onItemClick(id) }
			)
		}
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


// MY OWN CODE START HERE
//@Composable
//fun RestaurantScreen() {
//
//	// Not best way of achieving scroll, since it will load the whole list into memory
////		Column(Modifier.verticalScroll(rememberScrollState())) {
////			dummyRestaurants.forEach { restaurant: Restaurant ->
////				RestaurantItem(item = restaurant)
////			}
////		}
//
//	// A better way, since Lazy will only load those items currently visible on screen into memory -
//	// items is the list
//	// RestaurantItem is a composable function describing how each item in the list should be shown
//	LazyColumn(contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)) {
//		// populate the LazyColum with a list of dataclass objects.
//		// the list is called dummyRestaurants an is a list where each index in the list is a dataclass object -
//		// of type Restaurant
//		items(dummyRestaurants) { restaurant ->
//			RestaurantItem(item = restaurant)
//		}
//	}
//}
//
//// Argument item of type Data class Restaurant
//@Composable
//fun RestaurantItem(item: Restaurant) {
//	Card(elevation = 4.dp, modifier = Modifier.padding(8.dp)) {
//		Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
//			RestaurantIcon(Icons.Filled.Place, modifier = Modifier.weight(0.15f))
//			RestaurantDetails(
//				item.title,
//				item.description,
//				Modifier.weight(0.85f)
//			)
//		}
//	}
//}
//
//@Composable
//fun RestaurantIcon(
//	icon: ImageVector,
//	modifier: Modifier
//) {
//	Image(
//		imageVector = icon,
//		contentDescription = "Restaurant Icon",
//		modifier = Modifier.padding(8.dp)
//	)
//}
//
//@Composable
//fun RestaurantDetails(
//	title: String,
//	description: String,
//	modifier: Modifier
//) {
//	Column(modifier = modifier) {
//		Text(text = title, style = MaterialTheme.typography.h6)
//		CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//			Text(text = description, style = MaterialTheme.typography.body2)
//		}
//	}
//}