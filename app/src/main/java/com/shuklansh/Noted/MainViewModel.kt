package com.shuklansh.Noted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuklansh.Noted.NoteData.NoteEvents
import com.shuklansh.Noted.NoteData.NoteState
import com.shuklansh.Noted.data.Note
import com.shuklansh.Noted.data.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val dao : NoteDao
) : ViewModel() {


    private val _mutStateNote = MutableStateFlow(Note("","Heading"))

    private val _noteFromDb = _mutStateNote.flatMapLatest {
        dao.getTheNote()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Note("","Heading"))


    private var _noteState = MutableStateFlow(NoteState())

    private var _exists = MutableStateFlow(false)

    private val _existStatus = _exists.flatMapLatest {
        dao.hasItem()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val noteState = combine(_noteState,_noteFromDb,_existStatus) { noteState,noteFromDb,exist->
        noteState.copy(
            noteFromDb = if (exist){noteFromDb}else{_mutStateNote.value}
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NoteState())

    init {
        viewModelScope.launch(Dispatchers.IO) {

            dao.getTheNote().distinctUntilChanged().collect{ noteStoredInDb->
                if(noteStoredInDb!=null){
                    _noteState.update {
                        it.copy(
                            content = noteStoredInDb.content
                        )
                    }

                }
                else{
                    _noteState.update {
                        it.copy(
                            content = ""
                        )
                    }
                }
            }

        }

    }


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


