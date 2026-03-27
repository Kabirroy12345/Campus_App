package com.example.canteenapp.data

import androidx.compose.runtime.Immutable

/**
 * Represents a category of food items available in the canteen.
 */
enum class Category(val displayName: String) {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    SNACKS("Snacks"),
    BEVERAGES("Beverages"),
    SPECIAL("Today's Special")
}

/**
 * Data model for a single Canteen Menu Item.
 *
 * @param id Unique identifier for the item.
 * @param name Name of the dish.
 * @param description Short description of what is in the dish.
 * @param price Cost of the dish.
 * @param category The category this item belongs to.
 * @param isAvailable Boolean indicating if the item is currently in stock.
 * @param isVegetarian Flag to denote if the item is vegetarian.
 */
@Immutable
data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: Category,
    val imageUrl: String? = null,
    val isAvailable: Boolean = true,
    val isVegetarian: Boolean = true
)
