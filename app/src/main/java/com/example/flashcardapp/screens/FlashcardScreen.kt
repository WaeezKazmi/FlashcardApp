package com.example.flashcardapp.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(navController: NavController) {
    val flashcards = listOf(
        Pair(
            "Pythagorean theorem",
            "In a right-angled triangle, the square of the hypotenuse is equal to the sum of the squares of the other two sides."
        ),
        Pair(
            "Quadratic formula",
            "The solution to ax² + bx + c = 0 is given by x = [-b ± sqrt(b²-4ac)] / 2a."
        ),
        Pair(
            "Area of a circle",
            "The area of a circle is π times the square of its radius."
        )
    )

    var currentIndex by remember { mutableStateOf(0) }
    var isFront by remember { mutableStateOf(true) }

    // Animate rotationY with visible duration
    val animatedRotationY by animateFloatAsState(
        targetValue = if (isFront) 0f else 180f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "FlipAnimation"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flashcard Learning App") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A2B63),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .height(350.dp)
                    .graphicsLayer {
                        rotationY = animatedRotationY
                        cameraDistance = 12f * density
                    }
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val isBackVisible = animatedRotationY > 90f

                    if (isBackVisible) {
                        // ✅ Fix mirrored back by rotating content 180°
                        Text(
                            flashcards[currentIndex].second,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.graphicsLayer {
                                rotationY = 180f
                            }
                        )
                    } else {
                        Text(
                            flashcards[currentIndex].first,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                isFront = !isFront
            }) {
                Text("FLIP")
            }

            Spacer(Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        if (currentIndex > 0) {
                            currentIndex--
                            isFront = true // Reset to front
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Previous",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text("${currentIndex + 1}/${flashcards.size}")

                IconButton(
                    onClick = {
                        if (currentIndex < flashcards.size - 1) {
                            currentIndex++
                            isFront = true // Reset to front
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Next",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
