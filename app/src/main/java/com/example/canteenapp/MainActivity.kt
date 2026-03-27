package com.example.canteenapp

/* 
 * ==============================================================================
 * MAIN ACTIVITY & NAVIGATION (ENTRY POINT)
 * ==============================================================================
 * Role in Project: This file is the starting point of the entire Android Application.
 * Think of it as the "Host" that holds all of our Screens (Composables).
 * 
 * Flow: 
 * 1. OS Launches App -> MainActivity.onCreate() is triggered.
 * 2. It sets the theme (`CanteenAppTheme`) and calls `CanteenAppNavigation()`.
 * 3. The Navigation component decides which screen to show first (Login).
 * ==============================================================================
 */import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.canteenapp.ui.screens.LoginScreen
import com.example.canteenapp.ui.screens.MenuScreen
import com.example.canteenapp.ui.screens.ProfileScreen
import com.example.canteenapp.ui.screens.RegistrationScreen
import com.example.canteenapp.ui.screens.RemindersScreen
import com.example.canteenapp.ui.theme.CanteenAppTheme
import com.example.canteenapp.viewmodel.CanteenViewModel

class MainActivity : ComponentActivity() {

    // Handles the request for notification permissions on Android 13+
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ ->
        // Handle post-permission request if necessary
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request notifications permission if on API 33+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            CanteenAppTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CanteenAppNavigation()
                }
            }
        }
    }
}

/**
 * Handles the routing for the application.
 * Think of NavController as a "History Stack" that remembers where we came from.
 */
@Composable
fun CanteenAppNavigation() {
    val navController = rememberNavController()
    
    // IMPORTANT MVVM CONCEPT: 
    // We instantiate the CanteenViewModel HERE at the root of the navigation graph.
    // By passing this SAME 'sharedViewModel' down to MenuScreen and CartScreen,
    // they both look at the exact same cart data. If MenuScreen adds an item, 
    // CartScreen instantly sees it because they share this ViewModel!
    val sharedViewModel: CanteenViewModel = viewModel()

    // NavHost dictates the mapping between a String route (e.g., "login") and the actual Screen UI.
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("menu") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegistrationScreen(
                onRegisterSuccess = {
                    navController.navigate("menu") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack("login", inclusive = false) }
            )
        }
        composable("menu") {
            MenuScreen(
                viewModel = sharedViewModel,
                onNavigateToReminders = { navController.navigate("reminders") },
                onNavigateToCart = { navController.navigate("cart") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("cart") {
            com.example.canteenapp.ui.screens.CartScreen(
                viewModel = sharedViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("reminders") {
            RemindersScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogout = { 
                    navController.navigate("login") { 
                        popUpTo(navController.graph.id) { inclusive = true } 
                    } 
                }
            )
        }
    }
}
