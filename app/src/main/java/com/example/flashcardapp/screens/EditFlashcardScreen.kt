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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFlashcardScreen(
    navController: NavController,
    initialSubject: String,
    initialTopic: String,
    initialDetails: String
) {
    val existingSubjects = listOf("Mathematics", "Physics", "History")

    var subject by remember { mutableStateOf(initialSubject) }
    var topic by remember { mutableStateOf(initialTopic) }
    var details by remember { mutableStateOf(initialDetails) }
    var expanded by remember { mutableStateOf(false) }

    val suggestions = existingSubjects.filter {
        it.contains(subject, ignoreCase = true) && subject.isNotEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Flashcard") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A2B63), titleContentColor = Color.White)
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

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { /* TODO */ }) { Text("DELETE") }
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2B63), contentColor = Color.White)
                ) {
                    Text("SAVE")
                }
            }
        }
    }
}
