package com.example.totasks.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.totasks.repositories.TaskRepository
import nha.kc.kotlincode.models.Task
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(val taskRepository: TaskRepository) : ViewModel() {

    val _predictedTask = MutableLiveData<Task>()

    fun predictTaskSchedule(task: Task) = viewModelScope.launch {
        try {
            val result = taskRepository.predictTaskSchedule(task)
            _predictedTask.value = result // Cập nhật dữ liệu vào LiveData
            print("___${result}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            _predictedTask.value = null
        }
    }


    class TaskViewModelProviderFactory(val taskRepository: TaskRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TaskViewModel(taskRepository) as T
        }
    }
}