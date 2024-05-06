package com.example.a5046protoytpe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Exercise::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)

abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun exerciseDAO(): ExerciseDAO
    companion object {
        @Volatile
        private var INSTANCE: ExerciseDatabase? = null
        fun getDatabase(context: Context): ExerciseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseDatabase::class.java,
                    "exercise_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
