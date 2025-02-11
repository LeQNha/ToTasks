package com.example.totasks.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.totasks.R
import com.example.totasks.repositories.TaskRepository
import com.example.totasks.viewmodels.TaskViewModel

open class BaseActivity : AppCompatActivity() {
    lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        taskViewModelSetUp()
    }

    private fun taskViewModelSetUp() {
        val taskRepository = TaskRepository()
        val taskViewModelProviderFactory = TaskViewModel.TaskViewModelProviderFactory(taskRepository)
        taskViewModel = ViewModelProvider(this, taskViewModelProviderFactory).get(TaskViewModel::class.java)
    }
}