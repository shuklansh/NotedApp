package com.shuklansh.notesdiroomprac.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (

    var content : String,
    @PrimaryKey
    var heading : String
)