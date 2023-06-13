package com.shuklansh.notesdiroomprac.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun updateNoteContent(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Query("SELECT * FROM Note")
    fun getTheNote() : Flow<Note>

    @Query("SELECT EXISTS(SELECT * FROM Note)")
    fun hasItem(): Flow<Boolean>

}