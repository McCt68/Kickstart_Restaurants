package eu.example.kickstartrestaurants.holidayTest

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


// Kinda a copy if restaurantScreen using a viewModel, and my other list of holidays
//
//@Composable
//fun HolidayScreen() {
//
//	val viewModel: HolidaysViewModel = viewModel()
//
//	// See RestaurantScreen for detailed explanation of LazyColumn
//	LazyColumn(
//		contentPadding = PaddingValues(
//			vertical = 8.dp,
//			horizontal = 8.dp)
//	) {
//		items(viewModel.state.value) { holiday ->
//			HolidayItem(holiday) { id ->
//				viewModel.toggleFavorite(id)
//			}
//		}
//	}
//}
//
//@Composable
//fun HolidayItem(item: Holiday, onClick: (id: Int) -> Unit) {
//
//	val icon = if (item.isFavorite)
//		Icons.Filled.Favorite
//	else
//		Icons.Filled.FavoriteBorder
//
//	Card(
//		elevation = 4.dp,
//		modifier = Modifier
//			.padding(8.dp)) {
//		Row(
//			verticalAlignment = Alignment.CenterVertically,
//			modifier = Modifier
//				.padding(8.dp)) {
//			HolidayIcon(Icons.Filled.Place, modifier = Modifier.weight(0.15f))
//			HolidayDetails(
//				item.title,
//				item.description,
//				Modifier.weight(0.7f)
//			)
//			HolidayIcon(icon, Modifier.weight(0.15f)) {
//				onClick(item.id)
//			}
//		}
//	}
//}
//
//@Composable
//private fun HolidayIcon(
//	icon: ImageVector,
//	modifier: Modifier,
//	onClick: () -> Unit = {}
//) {
//	Image(
//		imageVector = icon,
//		contentDescription = "Holiday Icon",
//		modifier = Modifier
//			.padding(8.dp)
//			// Event to change state of favoriteState. called from the viewModel
//			.clickable { onClick() }
//
//	)
//}
//
//@Composable
//fun HolidayDetails(
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
//
//
//// This is just an example of using state
//// Not sure where this belongs ?? Maybe its just an example showing how to use state within compose
//@Composable
//fun NameInput() {
//
//	// a variable to hold the state
//	val textState: MutableState<String> = remember {
//		mutableStateOf("")
//	}
//
//	// Showing the state in a textField with textField value parameter -
//	// And updating it with TextField's onValueChange lambda parameter (Callback)
//	// when we update the state, the Ui will recompose, and textField will render the new input value from the keyboard
//	TextField(
//		value = textState.value,
//		onValueChange = { newValue ->
//			textState.value = newValue
//		}, label = { Text(text = "Your name") })
//}