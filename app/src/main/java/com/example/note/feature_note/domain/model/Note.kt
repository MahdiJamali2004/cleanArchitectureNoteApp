package com.example.note.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.note.ui.theme.BabyBlue
import com.example.note.ui.theme.LightGreen
import com.example.note.ui.theme.RedOrange
import com.example.note.ui.theme.RedPink
import com.example.note.ui.theme.Violet


@Entity
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {

    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)


    }

}


class InvalidNoteException(message: String) : Exception(message)