package com.example.totasks.repositories

import android.util.Log
import nha.kc.kotlincode.api.RetrofitInstance
import nha.kc.kotlincode.models.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository {

    suspend fun predictTaskSchedule(task:Task): Task?{
        return try {
            RetrofitInstance.taskApi.predictTasks(task) // ✅ Gọi API trong coroutine
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error fetching task: ${e.message}")
            null
        }
    }
}