package com.example.canteenapp.viewmodel

/* 
 * ==============================================================================
 * STATE MANAGEMENT & BUSINESS LOGIC (VIEWMODEL)
 * ==============================================================================
 * Role in Project: This file is the "Brain" of the Canteen App. It holds the
 * single source of truth for our user interface. By keeping the UI State (like
 * the cart's contents) separated from the UI Views (like MenuScreen), we ensure
 * the app survives screen rotations and maintains data integrity.
 * 
 * Flow: 
 * 1. UI receives a click (e.g., 'addToCart').
 * 2. UI invokes ViewModel's addToCart() function.
 * 3. ViewModel copies the current state, updates the cart map, and emits the new state.
 * 4. Jetpack Compose observes this new state and re-renders the views instantly.
 * ==============================================================================
 */import androidx.lifecycle.ViewModel
import com.example.canteenapp.data.Category
import com.example.canteenapp.data.MenuItem
import com.example.canteenapp.data.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * State class to hold the current UI state of our application.
 * Using an immutable state class makes Jetpack Compose rendering highly predictable.
 */
data class CanteenUiState(
    val menuItems: List<MenuItem> = MockData.menuItems,
    val favoriteIds: Set<String> = emptySet(),
    val selectedCategory: Category? = null,
    val cartItems: Map<String, Int> = emptyMap() // Map of ItemId to Quantity for hypothetical ordering
)

/**
 * CanteenViewModel manages the core business logic.
 * IN MVVM Architecture, this is the 'ViewModel' tier.
 * It strictly avoids importing any Android UI libraries.
 */
class CanteenViewModel : ViewModel() {

    // private mutable state flow
    private val _uiState = MutableStateFlow(CanteenUiState())
    // public immutable state flow read by the UI
    val uiState: StateFlow<CanteenUiState> = _uiState.asStateFlow()

    /**
     * Toggles an item in the favorites list.
     * If it exists, removes it. If it doesn't, adds it.
     */
    fun toggleFavorite(itemId: String) {
        _uiState.update { currentState ->
            val updatedFavorites = currentState.favoriteIds.toMutableSet()
            if (updatedFavorites.contains(itemId)) {
                updatedFavorites.remove(itemId)
            } else {
                updatedFavorites.add(itemId)
            }
            currentState.copy(favoriteIds = updatedFavorites)
        }
    }

    /**
     * Filters the menu list by a category.
     * Passing null removes the filter and shows all items.
     */
    fun selectCategory(category: Category?) {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = category)
        }
    }
    
    /**
     * Helper to get items filtered by the currently selected category.
     */
    fun getFilteredMenuItems(): List<MenuItem> {
        val currentState = _uiState.value
        val category = currentState.selectedCategory
        return if (category == null) {
            currentState.menuItems
        } else {
            currentState.menuItems.filter { it.category == category }
        }
    }

    /**
     * Adds an item to the cart, increasing its quantity by 1.
     */
    fun addToCart(item: MenuItem) {
        _uiState.update { currentState ->
            val currentQuantity = currentState.cartItems[item.id] ?: 0
            val updatedCart = currentState.cartItems.toMutableMap()
            updatedCart[item.id] = currentQuantity + 1
            currentState.copy(cartItems = updatedCart)
        }
    }

    /**
     * Removes 1 instance of an item from the cart. 
     * If quantity reaches 0, removes the item entirely from the cart map.
     */
    fun removeFromCart(item: MenuItem) {
        _uiState.update { currentState ->
            val currentQuantity = currentState.cartItems[item.id] ?: 0
            if (currentQuantity <= 0) return@update currentState
            
            val updatedCart = currentState.cartItems.toMutableMap()
            if (currentQuantity == 1) {
                updatedCart.remove(item.id)
            } else {
                updatedCart[item.id] = currentQuantity - 1
            }
            currentState.copy(cartItems = updatedCart)
        }
    }

    /**
     * Empties the shopping cart entirely.
     */
    fun clearCart() {
        _uiState.update { it.copy(cartItems = emptyMap()) }
    }

    /**
     * Calculates the total monetary value of all items currently in the cart.
     */
    fun getCartTotal(): Double {
        val state = _uiState.value
        var total = 0.0
        state.cartItems.forEach { (id, qty) ->
            val item = state.menuItems.find { it.id == id }
            if (item != null) {
                total += item.price * qty
            }
        }
        return total
    }
}
