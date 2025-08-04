package com.example.taskager

data class Task(
    var name: String,
    var isDone: Boolean = false,
    var completedDate: String? = null
)
