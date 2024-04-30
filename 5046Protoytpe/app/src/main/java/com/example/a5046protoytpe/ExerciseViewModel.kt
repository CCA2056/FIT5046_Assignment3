package com.example.a5046protoytpe

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val cRepository: ExerciseRepository
    init{
        cRepository = ExerciseRepository(application)
    }
    val allExercise: LiveData<List<Exercise>> = cRepository.allExercises.asLiveData()
    fun insertExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(exercise)
    }
    fun updateExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(exercise)
    }
    fun deleteExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(exercise)
    }

    @SuppressLint("NewApi")
    fun getTodayTotalDistance(): LiveData<Float> {
        val todayDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        return cRepository.getTodayTotalDistance(todayDate).asLiveData()
    }

}