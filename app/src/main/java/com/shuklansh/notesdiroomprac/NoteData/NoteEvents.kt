package com.shuklansh.notesdiroomprac.NoteData

import com.shuklansh.notesdiroomprac.data.Note

sealed class NoteEvents{

    data class setNoteContent(val noteContent : String) : NoteEvents()

    object saveNote : NoteEvents()

}
