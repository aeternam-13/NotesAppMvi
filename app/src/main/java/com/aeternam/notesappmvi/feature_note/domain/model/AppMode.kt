package com.aeternam.notesappmvi.feature_note.domain.model

sealed class AppMode {
    data object Api : AppMode()
    data object Disk : AppMode()
}
