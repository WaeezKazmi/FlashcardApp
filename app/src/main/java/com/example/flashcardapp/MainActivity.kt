package com.example.flashcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                val app = application as FlashcardApp
                val repository = app.repository

                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "deck_list"
                    ) {
                        composable("deck_list") {
                            DeckListScreen(navController, repository)
                        }
                        composable(
                            "flashcard_screen/{subject}",
                            arguments = listOf(navArgument("subject") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val subject = backStackEntry.arguments?.getString("subject") ?: ""
                            FlashcardScreen(navController, subject, repository)
                        }
                        composable(
                            route = "edit_flashcard?id={id}&subject={subject}&topic={topic}&details={details}",
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType; defaultValue = 0 },
                                navArgument("subject") { type = NavType.StringType; defaultValue = "" },
                                navArgument("topic") { type = NavType.StringType; defaultValue = "" },
                                navArgument("details") { type = NavType.StringType; defaultValue = "" }
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id") ?: 0
                            val subject = backStackEntry.arguments?.getString("subject") ?: ""
                            val topic = backStackEntry.arguments?.getString("topic") ?: ""
                            val details = backStackEntry.arguments?.getString("details") ?: ""
                            EditFlashcardScreen(navController, repository, subject, topic, details, id)
                        }
                    }
                }
            }
        }
    }
}