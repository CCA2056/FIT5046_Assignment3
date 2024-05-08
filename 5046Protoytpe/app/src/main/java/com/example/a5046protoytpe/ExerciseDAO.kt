package com.example.a5046protoytpe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDAO {
    @Query("SELECT * FROM Exercise")
    fun getAllExercise(): Flow<List<Exercise>>
    @Insert
    suspend fun insertExercise(exercise: Exercise)
    @Update
    suspend fun updateExercise(exercise: Exercise)
    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT SUM(distance) FROM Exercise WHERE date = :date")
    fun getTodayTotalDistance(date: String): Flow<Float>
}