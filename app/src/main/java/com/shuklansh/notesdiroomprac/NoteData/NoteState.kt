package com.shuklansh.notesdiroomprac.NoteData

import com.shuklansh.notesdiroomprac.data.Note

data class NoteState(
    var noteFromDb : Note? = Note("",""),
    var content : String = "",
    var heading : String = ""

)