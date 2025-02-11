package com.example.totasks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.totasks.R
import nha.kc.kotlincode.models.Task

class TasksScheduleAdapter : RecyclerView.Adapter<TasksScheduleAdapter.TaskScheduleViewHolder>() {
    inner class TaskScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTxtView = itemView.findViewById<TextView>(R.id.taskNameTxtView)
        val taskTimeTxtView = itemView.findViewById<TextView>(R.id.taskTimeTxtView)
    }

    private var differCallBack = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.TaskName == newItem.TaskName
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TasksScheduleAdapter.TaskScheduleViewHolder {
        return TaskScheduleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_task_layout, parent, false)
        )

//        return MemberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.assigned_member_rv_item_layout, parent, false))
    }


    override fun onBindViewHolder(
        holder: TasksScheduleAdapter.TaskScheduleViewHolder,
        position: Int
    ) {
        val currentTask = differ.currentList[position]
        holder.apply {
            taskNameTxtView.text = currentTask.TaskName
            taskTimeTxtView.text = currentTask.StartTime.toString() + " - " + currentTask.EndTime.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}