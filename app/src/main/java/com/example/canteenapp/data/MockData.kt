package com.example.canteenapp.data

/**
 * A mock dataset representing the daily inventory of the canteen.
 * This simulates data that would typically come from a backend server or a database.
 * We include various items across categories with varying availability to demonstrate UI states.
 */
object MockData {
    val menuItems = listOf(
        MenuItem(
            id = "1",
            name = "Masala Dosa",
            description = "Crispy crepe made from rice and lentils, filled with potato curry.",
            price = 60.0,
            category = Category.BREAKFAST,
            imageUrl = "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=500&q=80",
            isAvailable = true
        ),
        MenuItem(
            id = "2",
            name = "Idli Sambar",
            description = "Steamed rice cakes served with lentil soup and coconut chutney.",
            price = 40.0,
            category = Category.BREAKFAST,
            imageUrl = "https://images.unsplash.com/photo-1645177628172-a94c1f96e6db?auto=format&fit=crop&w=500&q=80",
            isAvailable = true
        ),
        MenuItem(
            id = "3",
            name = "Chicken Biryani",
            description = "Aromatic basmati rice cooked with spices and tender chicken pieces.",
            price = 120.0,
            category = Category.LUNCH,
            imageUrl = "https://images.unsplash.com/photo-1631515243349-e0cb75fb8d3a?auto=format&fit=crop&w=500&q=80",
            isAvailable = true,
            isVegetarian = false
        ),
        MenuItem(
            id = "4",
            name = "Veg Thali",
            description = "Complete meal with rice, dal, two curries, roti, and sweet.",
            price = 90.0,
            category = Category.LUNCH,
            imageUrl = "https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=500&q=80",
            isAvailable = false // Out of stock to show disabled state
        ),
        MenuItem(
            id = "5",
            name = "Samosa",
            description = "Deep-fried pastry filled with spiced potatoes and peas.",
            price = 15.0,
            category = Category.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=500&q=80",
            isAvailable = true
        ),
        MenuItem(
            id = "6",
            name = "Paneer Tikka Roll",
            description = "Grilled paneer wrapped in a paratha with mint sauce.",
            price = 70.0,
            category = Category.SNACKS,
            imageUrl = "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?auto=format&fit=crop&w=500&q=80",
            isAvailable = true
        ),
        MenuItem(
            id = "7",
            name = "Cold Coffee",
            description = "Chilled creamy coffee blended with ice cream.",
            price = 45.0,
            category = Category.BEVERAGES,
            imageUrl = "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=500&q=80",
            isAvailable = true
        ),
        MenuItem(
            id = "8",
            name = "Fresh Lime Soda",
            description = "Refreshing drink made with fresh lemon and soda.",
            price = 30.0,
            category = Category.BEVERAGES,
            imageUrl = "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=500&q=80",
            isAvailable = true
        ),
        MenuItem(
            id = "9",
            name = "Chef's Special Pasta",
            description = "Pasta cooked in mixed sauce with bell peppers and olives.",
            price = 110.0,
            category = Category.SPECIAL,
            imageUrl = "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?auto=format&fit=crop&w=500&q=80",
            isAvailable = true
        ),
        MenuItem(
            id = "10",
            name = "Chole Bhature",
            description = "Spicy chickpea curry served with fried flatbreads.",
            price = 80.0,
            category = Category.SPECIAL,
            imageUrl = "https://images.unsplash.com/photo-1626779815041-3b52d9a34102?auto=format&fit=crop&w=500&q=80",
            isAvailable = false // Sold out special
        )
    )
}
