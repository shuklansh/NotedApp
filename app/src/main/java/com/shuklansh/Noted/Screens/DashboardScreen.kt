package com.shuklansh.Noted.Screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.shuklansh.Noted.MainViewModel
import com.shuklansh.Noted.NoteData.NoteEvents
import com.shuklansh.Noted.data.NoteDatabase
import com.shuklansh.Noted.ui.theme.*


class DashboardScreen : Fragment() {
    


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {

            setContent {



                val sharedPref : SharedPreferences = requireActivity().applicationContext.getSharedPreferences("sharedPref",
                    Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                var savedCol = sharedPref.getString("STRING_COLOUR", "myPurple")

                var bgcol by remember{ mutableStateOf(

                    if (savedCol == "myPurple"){
                         myPurple

                    }else if (savedCol == "myYellow"){
                         myYellow
                    }else if(savedCol == "myPink"){
                         myPink
                    }else if(savedCol == "myBlue"){
                         myBlue
                    }else{
                         myGreen
                    }
                ) }


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

                var showMenu by remember { mutableStateOf(false) }

                var ctx = LocalContext.current

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    topBar = {

                        Column(Modifier.fillMaxWidth().background(Color(27, 27, 27, 255))) {
                            TopAppBar(
                                title = { Text(text = "Customise background colour")},
                                elevation = 0.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                                backgroundColor = Color.Magenta,
                                actions = {

                                    IconButton(onClick = { showMenu = !showMenu }) {
                                        Icon(Icons.Default.MoreVert,"")
                                    }

                                    DropdownMenu(expanded = showMenu , onDismissRequest = { showMenu = false }) {

                                        DropdownMenuItem(onClick = {
                                            bgcol = myYellow
                                            savedCol = "myYellow"
                                            editor.apply{
                                                putString("STRING_COLOUR",savedCol)
                                                Log.d("####",savedCol?:"null string")
                                            }.apply()
                                        }) {
                                            Row(Modifier.fillMaxWidth(1f)) {
                                                Text(text = "Yellow")
                                                Box(modifier = Modifier
                                                    .weight(1f)
                                                    .background(myYellow)
                                                    .clip(
                                                        CircleShape
                                                    ))
                                            }
                                        }

                                        DropdownMenuItem(onClick = {
                                            bgcol = myPink
                                            savedCol = "myPink"
                                            editor.apply{
                                                putString("STRING_COLOUR",savedCol)
                                                Log.d("####",savedCol?:"null string")
                                            }.apply()
                                        }) {
                                            Row(Modifier.fillMaxWidth(1f)) {
                                                Text(text = "Pink")
                                                Box(modifier = Modifier
                                                    .weight(1f)
                                                    .background(myPink)
                                                    .clip(
                                                        CircleShape
                                                    ))
                                            }
                                        }

                                        DropdownMenuItem(onClick = {
                                            bgcol = myGreen
                                            savedCol = "myGreen"
                                            editor.apply{
                                                putString("STRING_COLOUR",savedCol)
                                                Log.d("####",savedCol?:"null string")
                                            }.apply()
                                        }) {
                                            Row(Modifier.fillMaxWidth(1f)) {
                                                Text(text = "Green")
                                                Box(modifier = Modifier
                                                    .weight(1f)
                                                    .background(myGreen)
                                                    .clip(
                                                        CircleShape
                                                    ))
                                            }
                                        }

                                        DropdownMenuItem(onClick = {
                                            bgcol = myBlue
                                            savedCol = "myBlue"
                                            editor.apply{
                                                putString("STRING_COLOUR",savedCol)
                                                Log.d("####",savedCol?:"null string")
                                            }.apply()
                                        }) {
                                            Row(Modifier.fillMaxWidth(1f)) {
                                                Text(text = "Blue")
                                                Box(modifier = Modifier
                                                    .weight(1f)
                                                    .background(myBlue)
                                                    .clip(
                                                        CircleShape
                                                    ))
                                            }
                                        }


                                    }
                                }
                            )
                        }

                    }
//                    floatingActionButton = {
//                        FloatingActionButton(
//                            onClick = {
//                                vm.onEvent(NoteEvents.saveNote)
//
//                            },
//                            content = {
//                                IconButton(onClick = { vm.onEvent(NoteEvents.saveNote) }) {
//                                    Icon(
//                                        imageVector = Icons.Default.Add,
//                                        tint = Color(175, 135, 245, 255),
//                                        contentDescription = "SaveNote"
//                                    )
//                                }
//                            },
//                            contentColor = Color.Black,
//                            backgroundColor = Color(56, 37, 88, 255)
//                        )
//                    }
                ) {


//                    LaunchedEffect(key1 = true){
//                        vm.onEvent(NoteEvents.setNoteContent(state.noteFromDb.content))
//                    }

                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color(27, 27, 27, 255))
                            .padding(12.dp), horizontalAlignment = Alignment.End
                    ) {

                        TextField(
                            value = state.content,
                            //state.noteFromDb.content

                            //state.content
//                            if (state.noteFromDb != null) {
//                                state.noteFromDb.content
//                            } else {
//                                text
//                            }
                            onValueChange = {

//                                state.noteFromDb.content = it
//                                vm.onEvent(NoteEvents.setNoteContent(it))

                                //state.noteFromDb.content = it
//                                state.content = it
                                vm.onEvent(NoteEvents.setNoteContent(it))
                                vm.onEvent(NoteEvents.saveNote)

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
                                .fillMaxHeight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(bgcol)
                                .verticalScroll(rememberScrollState()),

                            )

//                        Text(
//                            state.noteFromDb.content,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight(0.4f)
//                        )

                        Spacer(Modifier.height(8.dp))

//                        IconButton(onClick = { vm.onEvent(NoteEvents.saveNote) }) {
//                            Icon(
//                                imageVector = Icons.Default.AddCircle,
//                                tint =Color(109, 70, 177, 255),
//                                contentDescription = "SaveNote"
//                            )
//                        }

                    }

                }

            }
        }
    }




}