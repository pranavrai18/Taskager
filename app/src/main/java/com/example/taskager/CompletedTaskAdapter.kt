package com.example.taskager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompletedTaskAdapter(
    private val completedTasks: List<Task>
) : RecyclerView.Adapter<CompletedTaskAdapter.CompletedViewHolder>() {

    class CompletedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: TextView = itemView.findViewById(R.id.completedTaskText)
        val dateText: TextView = itemView.findViewById(R.id.completedTaskDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.completed_task_item, parent, false)
        return CompletedViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompletedViewHolder, position: Int) {
        val task = completedTasks[position]
        holder.taskText.text = task.name
        holder.dateText.text = task.completedDate ?: ""
    }

    override fun getItemCount() = completedTasks.size
}
