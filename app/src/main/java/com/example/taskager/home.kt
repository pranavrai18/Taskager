package com.example.taskager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import androidx.core.content.edit

class HomeFragment : Fragment() {

    private lateinit var adapter: TaskAdapter

    private lateinit var heyUserText: TextView

    private lateinit var streakCounterText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TaskRepository.loadTasks(requireContext())
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        heyUserText = view.findViewById(R.id.heyUser)
        updateHeyUserGreeting()
        val recyclerView = view.findViewById<RecyclerView>(R.id.taskrecyclerview)
        streakCounterText = view.findViewById(R.id.streakCounter)

        // Set streak display
        val streakPrefs = requireContext().getSharedPreferences("user_streak", Context.MODE_PRIVATE)
        val streak = streakPrefs.getInt("streak", 0)
        streakCounterText.text = streak.toString()

        adapter = TaskAdapter(TaskRepository.tasks,
            onTaskCompleted = { position -> completeTask(position) },
            onTaskEdit = { position, oldName -> showEditTaskDialog(position, oldName) }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<FloatingActionButton>(R.id.plus).setOnClickListener {
            showAddTaskDialog()
        }
    }
    private fun updateHeyUserGreeting() {
        val prefs = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val name = prefs.getString("name", null)
        heyUserText.text = if (!name.isNullOrBlank()) "Hey $name !" else "Hey User !"
    }

    private fun showAddTaskDialog() {
        val editText = EditText(requireContext())
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Task")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val taskName = editText.text.toString()
                if (taskName.isNotBlank()) {
                    TaskRepository.tasks.add(Task(taskName))
                    adapter.notifyItemInserted(TaskRepository.tasks.size - 1)
                    TaskRepository.saveTasks(requireContext())
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditTaskDialog(position: Int, oldName: String) {
        val editText = EditText(requireContext())
        editText.setText(oldName)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Task")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newName = editText.text.toString()
                if (newName.isNotBlank()) {
                    TaskRepository.tasks[position].name = newName
                    adapter.notifyItemChanged(position)
                    TaskRepository.saveTasks(requireContext())
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun completeTask(position: Int) {
        val task = TaskRepository.tasks[position]
        if (!wasCompletedToday()) {
            incrementStreak()
        }
        task.isDone = true
        task.completedDate = LocalDate.now().toString()
        // Move to completedTasks
        TaskRepository.completedTasks.add(task)
        TaskRepository.tasks.removeAt(position)
        adapter.notifyItemRemoved(position)
        TaskRepository.saveTasks(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun wasCompletedToday(): Boolean {
        val streakPrefs = requireContext().getSharedPreferences("user_streak", Context.MODE_PRIVATE)
        val today = LocalDate.now().toString()
        val lastStreakDate = streakPrefs.getString("last_streak_date", null)
        return lastStreakDate == today
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun incrementStreak() {
        val streakPrefs = requireContext().getSharedPreferences("user_streak", Context.MODE_PRIVATE)
        val today = LocalDate.now().toString()
        val currentStreak = streakPrefs.getInt("streak", 0)

        streakPrefs.edit {
            putString("last_streak_date", today)
                .putInt("streak", currentStreak + 1)
        }
        streakCounterText.text = (currentStreak + 1).toString()
    }
    override fun onResume() {
        super.onResume()
        updateHeyUserGreeting()
    }

}
