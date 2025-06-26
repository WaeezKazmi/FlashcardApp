package com.example.flashcardapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.flashcardapp.data.Flashcard
import com.example.flashcardapp.data.FlashcardRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFlashcardScreen(
    navController: NavController,
    repository: FlashcardRepository,
    initialSubject: String = "",
    initialTopic: String = "",
    initialDetails: String = "",
    flashcardId: Int = 0
) {
    val existingSubjects by repository.allSubjects.collectAsState(initial = emptyList())
    var subject by remember { mutableStateOf(initialSubject) }
    var topic by remember { mutableStateOf(initialTopic) }
    var details by remember { mutableStateOf(initialDetails) }
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val suggestions = existingSubjects.filter {
        it.contains(subject, ignoreCase = true) && subject.isNotEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (flashcardId == 0) "Add Flashcard" else "Edit Flashcard") },
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
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Subject
            Text("Subject", style = MaterialTheme.typography.labelLarge)
            OutlinedTextField(
                value = subject,
                onValueChange = {
                    subject = it
                    expanded = it.isNotEmpty()
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter subject") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1A2B63),
                    focusedLabelColor = Color(0xFF1A2B63)
                )
            )

            DropdownMenu(
                expanded = expanded && suggestions.isNotEmpty(),
                onDismissRequest = { expanded = false }
            ) {
                suggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        text = { Text(suggestion) },
                        onClick = {
                            subject = suggestion
                            expanded = false
                        }
                    )
                }
            }

            // Topic
            Text("Topic", style = MaterialTheme.typography.labelLarge)
            OutlinedTextField(
                value = topic,
                onValueChange = { topic = it },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1A2B63),
                    focusedLabelColor = Color(0xFF1A2B63)
                )
            )

            // Details
            Text("Details", style = MaterialTheme.typography.labelLarge)
            OutlinedTextField(
                value = details,
                onValueChange = { details = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1A2B63),
                    focusedLabelColor = Color(0xFF1A2B63)
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (flashcardId != 0) {
                    OutlinedButton(onClick = {
                        scope.launch {
                            repository.delete(Flashcard(flashcardId, subject, topic, topic, details))
                            navController.popBackStack()
                        }
                    }) {
                        Text("DELETE")
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                Button(
                    onClick = {
                        val flashcard = Flashcard(
                            id = flashcardId,
                            subject = subject,
                            topic = topic,
                            frontText = topic,
                            backText = details
                        )

                        scope.launch {
                            if (flashcardId == 0) {
                                repository.insert(flashcard)
                            } else {
                                repository.update(flashcard)
                            }
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A2B63),
                        contentColor = Color.White
                    )
                ) {
                    Text("SAVE")
                }
            }
        }
    }
}