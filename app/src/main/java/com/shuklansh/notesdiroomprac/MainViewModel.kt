package com.shuklansh.notesdiroomprac

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuklansh.notesdiroomprac.NoteData.NoteEvents
import com.shuklansh.notesdiroomprac.NoteData.NoteState
import com.shuklansh.notesdiroomprac.data.Note
import com.shuklansh.notesdiroomprac.data.NoteDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val dao : NoteDao
) : ViewModel() {


    private val _mutStateNote = MutableStateFlow(Note("",""))


    private val _noteFromDb = _mutStateNote.flatMapLatest {
        dao.getTheNote()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Note("","Heading"))

    private var _noteState = MutableStateFlow(NoteState())


    val noteState = combine(_noteState,_noteFromDb) { noteState,noteFromDb->
        noteState.copy(
            noteFromDb = noteFromDb
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NoteState())


//    fun checkIfNoteNotThere() : String{
//
//        if(noteState.value.noteFromDb!=null){
//            return noteState.value.noteFromDb.content
//        }
//        else{
//            return ""
//        }
//
//    }

//      init {
//
//
//            _noteState.update {
//                it.copy(
//                    content = _noteState.value.noteFromDb.content
//                )
//            }
//
//
//    }


    fun onEvent(event : NoteEvents){
        when(event){
            is NoteEvents.setNoteContent ->
            {
                _noteState.update {
                    it.copy(
                        content = event.noteContent
                    )
                }

            }
            NoteEvents.saveNote -> {
                val contentLatest = noteState.value.content
                val notetobeupserted = Note(content = contentLatest, heading = "Heading")

                viewModelScope.launch {
                    dao.updateNoteContent(notetobeupserted)
                }

                }

            }
        }
    }


