package com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.aeternam.notesappmvi.feature_note.domain.model.Note
import com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.AddEditNoteStateHolder
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.focus.FocusState
import com.aeternam.notesappmvi.feature_note.presentation.add_edit_note_screen.composables.TransparentHintTextField

@Composable
fun AddEditNoteScreenEditing(
    stateHolder: AddEditNoteStateHolder,
    changeColor: (Int) -> Unit,
    enteredTitle: (String) -> Unit,
    changeTitleFocus: (FocusState) -> Unit,
    enteredContent: (String) -> Unit,
    changeContentFocus: (FocusState) -> Unit,
) {
    Column {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(Note.noteColors) {
                val colorInt = it.toArgb()
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .shadow(25.dp, CircleShape)
                        .clip(CircleShape)
                        .background(it)
                        .border(
                            width = 3.dp, color = if (stateHolder.color.toInt() == colorInt) {
                                Color.Black
                            } else Color.Transparent, shape = CircleShape
                        )
                        .clickable { changeColor(colorInt) }
                        .padding(horizontal = 2.dp)
                )

            }


        }

        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = stateHolder.title.text,
                hint = stateHolder.title.hint,
                singleLine = true, onValueChange = { enteredTitle(it) },
                onFocusChange = { changeTitleFocus(it) },
                isHintVisible = stateHolder.title.isHintVisible,
                textStyle = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = stateHolder.content.text,
                hint = stateHolder.content.hint,
                modifier = Modifier.fillMaxHeight(),
                onValueChange = { enteredContent(it) },
                onFocusChange = { changeContentFocus(it) },
                isHintVisible = stateHolder.content.isHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}