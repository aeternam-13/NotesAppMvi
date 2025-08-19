package com.aeternam.notesappmvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.screen.AddEditNoteScreen
import com.aeternam.notesappmvi.feature_note.presentation.destinations.AddEditNoteDestination
import com.aeternam.notesappmvi.feature_note.presentation.destinations.NotesScreenDestination
import com.aeternam.notesappmvi.feature_note.presentation.notes_screen.screen.NotesScreen
import com.aeternam.notesappmvi.ui.theme.NotesAppMviTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppMviTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NotesScreenDestination
                ){
                    composable<NotesScreenDestination>{
                        NotesScreen(navController = navController)
                    }
                    composable<AddEditNoteDestination> { navBackStackEntry ->
                        val args = navBackStackEntry.toRoute<AddEditNoteDestination>()
                        AddEditNoteScreen(
                            navController = navController,
                            noteId = args.noteId,
                            noteColor = args.noteColor
                        )
                    }
                }
            }
        }
    }
}
