package com.shuklansh.notesdiroomprac.NoteData

import com.shuklansh.notesdiroomprac.data.Note

data class NoteState(
    val noteFromDb : Note = Note("",""),
    val content : String = "",
    val heading : String = ""

)