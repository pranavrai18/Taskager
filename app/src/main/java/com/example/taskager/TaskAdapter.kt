package com.example.taskager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskCompleted: (Int) -> Unit,
    private val onTaskEdit: (Int, String) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskCheckBox: CheckBox = view.findViewById(R.id.taskCheckBox)
        val taskText: TextView = view.findViewById(R.id.taskText)
        val editButton: ImageButton = view.findViewById(R.id.editTaskButton)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteTaskButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskText.text = task.name
        holder.taskCheckBox.setOnCheckedChangeListener(null)
        holder.taskCheckBox.isChecked = false // Default unchecked
        holder.taskCheckBox.isEnabled = !task.isDone
        holder.taskCheckBox.isChecked = task.isDone

        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !task.isDone) {
                onTaskCompleted(position)
            }
        }
        holder.deleteButton.setOnClickListener {
            tasks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tasks.size)
            TaskRepository.saveTasks(holder.itemView.context)
        }
        holder.editButton.setOnClickListener {
            onTaskEdit(position, task.name)
        }
    }

    override fun getItemCount() = tasks.size
}

