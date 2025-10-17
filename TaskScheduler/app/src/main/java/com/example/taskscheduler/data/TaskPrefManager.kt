package com.example.taskscheduler.data

import android.content.Context
import com.example.taskscheduler.model.TaskModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskPrefManager(private val context: Context) {
    private val PREF_NAME = "task_pref"
    private val KEY_TASK_LIST = "task_list"
    private val gson = Gson()

    fun saveTask(task: TaskModel) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val currentList = getTasks().toMutableList()
        currentList.add(task)
        val jsonString = gson.toJson(currentList)
        editor.putString(KEY_TASK_LIST, jsonString)
        editor.apply()
    }
    fun getTasks(): List<TaskModel> {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString(KEY_TASK_LIST, null)
        return if (jsonString != null) {
            val type = object : TypeToken<List<TaskModel>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            emptyList()
        }
    }
}
