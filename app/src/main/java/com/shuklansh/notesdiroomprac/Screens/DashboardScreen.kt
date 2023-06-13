package com.shuklansh.notesdiroomprac.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.shuklansh.notesdiroomprac.MainViewModel
import com.shuklansh.notesdiroomprac.NoteData.NoteEvents
import com.shuklansh.notesdiroomprac.R
import com.shuklansh.notesdiroomprac.data.Note
import com.shuklansh.notesdiroomprac.data.NoteDatabase


class DashboardScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {


                val db by lazy {
                    Room.databaseBuilder(
                        context = context,
                        klass = NoteDatabase::class.java,
                        name = "NoteDb"
                    ).fallbackToDestructiveMigration().build()
                }

                val vm: MainViewModel by viewModels(
                    factoryProducer = {
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return MainViewModel(db.dao) as T
                            }

                        }
                    }
                )


                val state = vm.noteState.collectAsState().value

                Scaffold(Modifier.fillMaxSize()) {

//                    LaunchedEffect(key1 = true){
//                        vm.onEvent(NoteEvents.setNoteContent(state.noteFromDb.content))
//                    }

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(4.dp), horizontalAlignment = Alignment.End
                    ) {

                        TextField(
                            value = state.content 
                            //state.noteFromDb.content

                            //state.content
//                            if (state.noteFromDb != null) {
//                                state.noteFromDb.content
//                            } else {
//                                text
//                            }
                            ,
                            onValueChange = {

//                                state.noteFromDb.content = it
//                                vm.onEvent(NoteEvents.setNoteContent(it))

                                //state.noteFromDb.content = it
//                                state.content = it
                                vm.onEvent(NoteEvents.setNoteContent(it))

//                                if (state.noteFromDb != null) {
//                                    state.noteFromDb.content = it
//                                    vm.onEvent(NoteEvents.setNoteContent(it))
//                                } else {
//                                    text = it
//                                    vm.onEvent(NoteEvents.setNoteContent(it))
//                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.9f),

                            )

//                        Text(
//                            state.noteFromDb.content,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight(0.4f)
//                        )

                        Spacer(Modifier.height(8.dp))

                        IconButton(onClick = { vm.onEvent(NoteEvents.saveNote) }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "SaveNote")
                        }

                    }

                }

            }
        }
    }


}