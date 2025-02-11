package com.example.totasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.totasks.R
import com.example.totasks.adapters.TasksScheduleAdapter
import com.example.totasks.databinding.ActivityTasksSchedulePrototypeBinding
import com.example.totasks.interfaces.TaskDialogListener
import com.example.totasks.ui.activities.BaseActivity
import com.example.totasks.ui.fragments.AddTaskDialogFragment
import com.example.totasks.viewmodels.TaskViewModel
import nha.kc.kotlincode.models.Task

class TasksSchedulePrototype : BaseActivity(), TaskDialogListener {
    private lateinit var binding: ActivityTasksSchedulePrototypeBinding

    private lateinit var tasksScheduleAdapter: TasksScheduleAdapter

    lateinit var predictedTaskArrayList: ArrayList<Task>
    lateinit var taskArrayList: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksSchedulePrototypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskArrayList = arrayListOf()
        predictedTaskArrayList = arrayListOf()

        taskScheduleRvSetUp()
        onClickListennerSetUp()
        taskScheduleRvUpdate()
    }

    fun taskScheduleRvSetUp(){
        tasksScheduleAdapter = TasksScheduleAdapter()

        binding.taskScheduleRv.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = tasksScheduleAdapter
        }

//        taskArrayList.add(Task("Eat", "Personal", "Normal", "Monday", 30,"7:00", "7:30"))
//        taskArrayList.add(Task("Study", "Education", "Important", "Tuesday", 120,"9:00", "11:00"))
//        taskArrayList.add(Task("Eat", "Play", "Normal", "Wednesday", 60,"17:00", "18:00"))

//        tasksScheduleAdapter.differ.submitList(taskArrayList)
        tasksScheduleAdapter.differ.submitList(predictedTaskArrayList)
    }

    fun onClickListennerSetUp(){

        binding.addTaskFab.setOnClickListener {
            val dialog = AddTaskDialogFragment()
            dialog.show(supportFragmentManager, "AddTaskDialogFragment")
        }

        binding.predictTaskScheduleBtn.setOnClickListener {
            for (task in taskArrayList){
                taskViewModel.predictTaskSchedule(task)
            }
            binding.taskNumberTxtView.text = "0"
            predictedTaskArrayList.clear()
        }
    }

    fun taskScheduleRvUpdate(){
        taskViewModel._predictedTask.observe(this){ predictedTask ->
            predictedTaskArrayList.add(predictedTask)
            tasksScheduleAdapter.differ.submitList(predictedTaskArrayList.toList()) // Cập nhật danh sách
            tasksScheduleAdapter.notifyDataSetChanged()
        }
    }

    override fun onTaskAdded(task: Task) {
        taskArrayList.add(task)
        binding.taskNumberTxtView.text = (binding.taskNumberTxtView.text.toString().toInt() + 1).toString()
//        tasksScheduleAdapter.differ.submitList(taskArrayList.toList()) // Cập nhật danh sách
//        tasksScheduleAdapter.notifyDataSetChanged()
    }
}