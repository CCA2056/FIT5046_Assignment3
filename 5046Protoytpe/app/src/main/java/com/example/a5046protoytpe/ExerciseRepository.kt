package com.example.a5046protoytpe

import android.app.Application
import kotlinx.coroutines.flow.Flow
class ExerciseRepository (application: Application) {
    private var exerciseDao: ExerciseDAO =
        ExerciseDatabase.getDatabase(application).exerciseDAO()
    val allExercises: Flow<List<Exercise>> = exerciseDao.getAllExercise()

    suspend fun insert(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }
    suspend fun delete(exercise: Exercise) {
        exerciseDao.deleteExercise(exercise)
    }
    suspend fun update(exercise: Exercise) {
        exerciseDao.updateExercise(exercise)
    }
    fun getTodayTotalDistance(date: String): Flow<Float> {
        return exerciseDao.getTodayTotalDistance(date)
    }


}
