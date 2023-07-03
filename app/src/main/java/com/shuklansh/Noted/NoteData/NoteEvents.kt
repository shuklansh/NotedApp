package com.shuklansh.Noted.NoteData

sealed class NoteEvents{

    data class setNoteContent(val noteContent : String) : NoteEvents()

    object saveNote : NoteEvents()

}
