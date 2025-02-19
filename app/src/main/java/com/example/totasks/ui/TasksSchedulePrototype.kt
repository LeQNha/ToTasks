package com.example.totasks.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.totasks.adapters.TasksScheduleAdapter
import com.example.totasks.databinding.ActivityTasksSchedulePrototypeBinding
import com.example.totasks.interfaces.TaskDialogListener
import com.example.totasks.models.Day
import com.example.totasks.ui.activities.BaseActivity
import com.example.totasks.ui.fragments.AddTaskDialogFragment
import nha.kc.kotlincode.models.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TasksSchedulePrototype : BaseActivity(), TaskDialogListener {
    private lateinit var binding: ActivityTasksSchedulePrototypeBinding

    private lateinit var tasksScheduleAdapter: TasksScheduleAdapter

    lateinit var predictedTaskArrayList: ArrayList<Task>
    lateinit var taskArrayList: ArrayList<Task>

    lateinit var selectedDate: Day


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksSchedulePrototypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskArrayList = arrayListOf()
        predictedTaskArrayList = arrayListOf()

        val calendar = Calendar.getInstance()
        selectedDate = Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
//        selectedDate.year = calendar.get(Calendar.YEAR)
//        selectedDate.month = calendar.get(Calendar.MONTH)
//        selectedDate.day = calendar.get(Calendar.DAY_OF_MONTH)


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


        taskViewModel.getTasks()

        taskViewModel._tasks.observe(this){ tasks ->
            for (t in tasks) {
                predictedTaskArrayList.add(t)
            }
            taskArrayList.sortBy {
                it.StartTimeInMinute
            }
            tasksScheduleAdapter.differ.submitList(predictedTaskArrayList.toList()) // Cập nhật danh sách
            tasksScheduleAdapter.notifyDataSetChanged()
        }

//        tasksScheduleAdapter.differ.submitList(predictedTaskArrayList)
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

        binding.openCalendarBtn.setOnClickListener {
            showDatePickerDialog()
        }
    }

    fun taskScheduleRvUpdate(){
//        taskViewModel._predictedTask.observe(this){ predictedTask ->
//            predictedTaskArrayList.add(predictedTask)
//            predictedTaskArrayList.sortBy {
//                it.StartTimeInMinute
//            }
//            tasksScheduleAdapter.differ.submitList(predictedTaskArrayList.toList()) // Cập nhật danh sách
//            tasksScheduleAdapter.notifyDataSetChanged()
//        }


        var predictedTask: Task? = null
        taskViewModel._predictedTask.observe(this){ it ->
            predictedTask = it
            addTaskToDatabase(predictedTask)
        }
    }

    fun addTaskToDatabase(predictedTask: Task?){
        predictedTask?.let {
            taskViewModel.addTask(it)
        }
    }

    override fun onTaskAdded(task: Task) {
        taskArrayList = predictedTaskArrayList
        taskArrayList.add(task)
        binding.taskNumberTxtView.text = (binding.taskNumberTxtView.text.toString().toInt() + 1).toString()

        tasksScheduleAdapter.differ.submitList(taskArrayList.toList()) // Cập nhật danh sách
        tasksScheduleAdapter.notifyDataSetChanged()

//        taskViewModel.addTask(task)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Chuyển đổi ngày thành định dạng mong muốn
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)

            selectedDate.year = selectedYear
            selectedDate.month = selectedMonth
            selectedDate.day = selectedDay

            // Lấy tên ngày trong tuần (ví dụ: Monday)
            val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            val dayOfWeek = dayOfWeekFormat.format(selectedCalendar.time)

            // Lấy ngày tháng năm (định dạng: dd/MM/yyyy)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.format(selectedCalendar.time)

            // Hiển thị ngày được chọn
            binding.dayOfWeekTxtView.text = dayOfWeek
            binding.dayTxtView.text = date
//        }, year, month, day)
        }, selectedDate.year, selectedDate.month, selectedDate.day)

        datePickerDialog.show()
    }
}