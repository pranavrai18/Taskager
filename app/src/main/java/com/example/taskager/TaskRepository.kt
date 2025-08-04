package com.example.taskager

import android.content.Context
import com.google.gson.Gson
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken

object TaskRepository {
    val tasks = mutableListOf<Task>()            // Active/incomplete tasks
    val completedTasks = mutableListOf<Task>()   // Done tasks

        fun saveTasks(context: Context) {
            val prefs = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)
            val gson = Gson()
            prefs.edit()
                .putString("active_tasks", gson.toJson(tasks))
                .putString("completed_tasks", gson.toJson(completedTasks))
                .apply()
        }

        fun loadTasks(context: Context) {
            val prefs = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)
            val gson = Gson()
            val activeJson = prefs.getString("active_tasks", null)
            val completedJson = prefs.getString("completed_tasks", null)
            val listType = object : TypeToken<MutableList<Task>>() {}.type
            tasks.clear()
            completedTasks.clear()
            if (activeJson != null) {
                tasks.addAll(gson.fromJson(activeJson, listType))
            }
            if (completedJson != null) {
                completedTasks.addAll(gson.fromJson(completedJson, listType))
            }
        }
    }


