package com.shuklansh.Noted.NoteData

import com.shuklansh.Noted.data.Note

data class NoteState(
    var noteFromDb : Note? = Note("",""),
    var content : String = "",
    var heading : String = ""

)