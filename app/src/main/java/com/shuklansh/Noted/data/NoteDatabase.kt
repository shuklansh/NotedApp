package com.shuklansh.Noted.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 2,
    entities = [Note::class]
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val dao : NoteDao

//    companion object{
//        @Volatile
//        private var INSTANCE : NoteDatabase? = null
//        fun getNoteDb(context: Context) : NoteDatabase{
//
//            if(INSTANCE==null){
//                Room.databaseBuilder(
//                    context = context,
//                    klass = NoteDatabase::class.java,
//                    name = "NoteDb"
//                    ).fallbackToDestructiveMigration().build()
//            }
//            return INSTANCE!!
//
//        }
//    }

}