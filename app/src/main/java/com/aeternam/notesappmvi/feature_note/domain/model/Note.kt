package com.aeternam.notesappmvi.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Long,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors =
            listOf(
                Color(0xFFFF8C93),
                Color(0xFF51C79B),
                Color(0xFFDC6958),
                Color(0xFF6AE78C),
                Color(0xFFD896FF),
                Color(0xFFE2D528),
                Color(0xFF28C6E2),
                Color(0xFFFD6FFF),
                Color(0xFFC0C0C0),
                Color(0xFFFFC754),
                Color(0xFF4BFCFF),
            )

    }
}