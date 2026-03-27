package com.example.canteenapp.ui.screens

/* 
 * ==============================================================================
 * MENU DASHBOARD (THE PRIMARY UI)
 * ==============================================================================
 * Role in Project: This screen represents the "Dashboard" of the application. 
 * Students browse food categories, scroll through items, and hit Add.
 * 
 * Compose Magic: Look at `val uiState by viewModel.uiState.collectAsState()`.
 * This one line connects our UI Directly to the brain (CanteenViewModel). Next time
 * the StateFlow updates, Compose recalculates and redraws only what changed!
 * ==============================================================================
 */import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.canteenapp.data.Category
import com.example.canteenapp.ui.components.ItemCard
import com.example.canteenapp.viewmodel.CanteenViewModel

/**
 * Main screen for browsing the canteen menu.
 * Contains a scrollable horizontal list of categories, and a vertical list of menu items.
 * Optimized for performance with `remember` and item keys.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    viewModel: CanteenViewModel,
    onNavigateToReminders: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // PERFORMANCE FIX: Memoize the filtered list so it doesn't recalculate on every UI frame layout
    val filteredItems by remember(uiState.selectedCategory, uiState.menuItems) {
        derivedStateOf {
            if (uiState.selectedCategory == null) {
                uiState.menuItems
            } else {
                uiState.menuItems.filter { it.category == uiState.selectedCategory }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Good Morning,",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "Hungry?",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(onClick = onNavigateToCart) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    TextButton(
                        onClick = onNavigateToReminders,
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Text("Reminders", fontWeight = FontWeight.Bold)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Category Filter Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    CategoryChip(
                        text = "All",
                        isSelected = uiState.selectedCategory == null,
                        onClick = { viewModel.selectCategory(null) }
                    )
                }
                items(Category.values()) { category ->
                    CategoryChip(
                        text = category.displayName,
                        isSelected = uiState.selectedCategory == category,
                        onClick = { viewModel.selectCategory(category) }
                    )
                }
            }

            // Menu Items List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // PERFORMANCE FIX: using item id as key prevents unnecessary recompositions when scrolling
                items(
                    items = filteredItems,
                    key = { item -> item.id }
                ) { item ->
                    ItemCard(
                        item = item,
                        isFavorite = uiState.favoriteIds.contains(item.id),
                        onFavoriteClick = { viewModel.toggleFavorite(item.id) },
                        quantity = uiState.cartItems[item.id] ?: 0,
                        onAddClick = { viewModel.addToCart(item) },
                        onRemoveClick = { viewModel.removeFromCart(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = Color.Transparent, // Managed by Box background
        shadowElevation = if (isSelected) 6.dp else 2.dp,
        modifier = Modifier.height(44.dp)
    ) {
        val backgroundModifier = if (isSelected) {
            Modifier.background(Brush.horizontalGradient(listOf(com.example.canteenapp.ui.theme.OrangeGradientStart, com.example.canteenapp.ui.theme.OrangeGradientEnd)))
        } else {
            Modifier.background(backgroundColor)
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = backgroundModifier.padding(horizontal = 24.dp).fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = contentColor
            )
        }
    }
}
