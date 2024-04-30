package com.example.a5046protoytpe
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val distance: Float,
    val time: Int,
    val date: String
)
