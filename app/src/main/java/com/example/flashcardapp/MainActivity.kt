package com.example.flashcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.flashcardapp.screens.DeckListScreen
import com.example.flashcardapp.screens.EditFlashcardScreen
import com.example.flashcardapp.screens.FlashcardScreen
import com.example.flashcardapp.ui.theme.FlashcardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "deck_list") {
                        composable("deck_list") {
                            DeckListScreen(navController)
                        }
                        composable("flashcard_screen") {
                            FlashcardScreen(navController)
                        }
                        composable(
                            route = "edit_flashcard?subject={subject}&topic={topic}&details={details}",
                            arguments = listOf(
                                navArgument("subject") { type = NavType.StringType; defaultValue = "" },
                                navArgument("topic") { type = NavType.StringType; defaultValue = "" },
                                navArgument("details") { type = NavType.StringType; defaultValue = "" }
                            )
                        ) { backStackEntry ->
                            val subject = backStackEntry.arguments?.getString("subject") ?: ""
                            val topic = backStackEntry.arguments?.getString("topic") ?: ""
                            val details = backStackEntry.arguments?.getString("details") ?: ""
                            EditFlashcardScreen(navController, subject, topic, details)
                        }
                    }
                }
            }
        }
    }
}
