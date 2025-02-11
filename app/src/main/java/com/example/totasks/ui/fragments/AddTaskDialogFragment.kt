package com.example.totasks.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.totasks.R
import com.example.totasks.databinding.FragmentAddTaskDialogBinding
import com.example.totasks.interfaces.TaskDialogListener
import nha.kc.kotlincode.models.Task

class AddTaskDialogFragment : DialogFragment() {

    private var _binding: FragmentAddTaskDialogBinding? = null
    private val binding get() = _binding!!
    private var listener: TaskDialogListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_task_dialog, container, false)
        _binding = FragmentAddTaskDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Lấy ra window của dialog và chỉnh kích thước
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_corner_background))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TaskDialogListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement TaskDialogListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListenerSetUp()
    }

    fun onClickListenerSetUp() {
        binding.addTaskButton.setOnClickListener {
            val name = binding.taskNameEditText.text.toString()
            val type = binding.taskTypeEditText.text.toString()
            val importance = binding.taskImportanceEditText.text.toString()
            val day = binding.taskDayEditText.text.toString()
            // Chuyển đổi duration từ String thành Int an toàn
            val durationText = binding.taskDurationEditText.text.toString()
            val duration: Int = if (durationText.isNotEmpty()) durationText.toInt() else 0
            val startTime = binding.taskStartTimeEditText.text.toString()
            val endTime = binding.taskEndTimeEditText.text.toString()

            if (name.isNotEmpty() && day.isNotEmpty()) {
                val newTask = Task(name, type, importance, day, duration,0, startTime,0, endTime)
                listener?.onTaskAdded(newTask)
                dismiss() // Đóng dialog sau khi thêm task
            }
        }
    }

//    interface TaskDialogListener {
//        fun onTaskAdded(task: Task)
//    }
}