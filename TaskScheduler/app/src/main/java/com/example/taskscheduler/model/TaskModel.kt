package com.example.taskscheduler.model

data class TaskModel(
    val name: String,
    val date: String,
    val time: String,
    val category: String,
    val reminder: Boolean,
    var isDone: Boolean = false
)