package com.example.taskscheduler

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.taskscheduler.data.TaskDatabaseHelper
import com.example.taskscheduler.model.TaskModel
import android.content.Intent
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale
import android.widget.CheckBox
import android.graphics.Color

class HomeActivity : AppCompatActivity() {
    private lateinit var taskContainer: LinearLayout
    private lateinit var completedContainer: LinearLayout
    private lateinit var overdueContainer: LinearLayout
    private lateinit var completedSection: LinearLayout
    private lateinit var overdueSection: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        taskContainer = findViewById(R.id.taskContainer)
        completedContainer = findViewById(R.id.completedContainer)
        overdueContainer = findViewById(R.id.overdueContainer)
        completedSection = findViewById(R.id.completedSection)
        overdueSection = findViewById(R.id.overdueSection)

        loadTasksToHome()

        val addTaskButton = findViewById<View>(R.id.btnAddTask)
        addTaskButton?.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadTasksToHome() {
        val db = TaskDatabaseHelper(this)
        val taskList = db.getAllTasks()
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        //task shalgah baigaa esehiig
        if (taskList.isEmpty()) {
            hideAllSections()
            val emptyText = TextView(this).apply {
                text = "No tasks yet"
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.darker_gray))
            }
            taskContainer.addView(emptyText)
            return
        }

        //hugatsaa n duusaagui mun hiigegui bga
        val upcomingIncompleteTasks = taskList.filter { task ->
            val taskDateTime = dateFormat.parse("${task.date} ${task.time}")
            taskDateTime != null && taskDateTime.after(currentTime) && !task.isDone
        }

        //hugatsaa n duussan buguud hiigegui orhison
        val pastIncompleteTasks = taskList.filter { task ->
            val taskDateTime = dateFormat.parse("${task.date} ${task.time}")
            taskDateTime != null && taskDateTime.before(currentTime) && !task.isDone
        }

        //hiisen daalgavar
        val completedTasks = taskList.filter { it.isDone }

        taskContainer.removeAllViews()
        if (upcomingIncompleteTasks.isNotEmpty()) {
            findViewById<LinearLayout>(R.id.taskSection).visibility = View.VISIBLE
            for (task in upcomingIncompleteTasks) {
                addTaskView(task)
            }
        } else {
            findViewById<LinearLayout>(R.id.taskSection).visibility = View.GONE
        }


        if (pastIncompleteTasks.isNotEmpty()) {
            overdueSection.visibility = View.VISIBLE
            overdueContainer.removeAllViews()
            for (task in pastIncompleteTasks) {
                addOverdueTaskView(task)
            }
        } else {
            overdueSection.visibility = View.GONE
        }

        if (completedTasks.isNotEmpty()) {
            completedSection.visibility = View.VISIBLE
            completedContainer.removeAllViews()
            for (task in completedTasks) {
                addCompletedTaskView(task)
            }
        } else {
            completedSection.visibility = View.GONE
        }
    }
    private fun hideAllSections() {
        completedSection.visibility = View.GONE
        overdueSection.visibility = View.GONE
    }

    private fun addTaskView(task: TaskModel) {
        val taskView = layoutInflater.inflate(R.layout.item_task_card, taskContainer, false)

        val tvTaskTitle = taskView.findViewById<TextView>(R.id.tvTaskCardTitle)
        val tvTaskTime = taskView.findViewById<TextView>(R.id.tvTaskCardTime)
        val checkTaskDone = taskView.findViewById<CheckBox>(R.id.checkTaskDone)

        tvTaskTitle.text = task.name
        tvTaskTime.text = "${task.date}  ${task.time}"
        checkTaskDone.isChecked = task.isDone

        checkTaskDone.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
            TaskDatabaseHelper(this).updateTask(task)
            taskContainer.removeView(taskView)
            addCompletedTaskView(task)
        }
        taskContainer.addView(taskView)
    }

    private fun addCompletedTaskView(task: TaskModel) {
        val taskView = layoutInflater.inflate(R.layout.item_task_card, completedContainer, false)
        val tvTaskTitle = taskView.findViewById<TextView>(R.id.tvTaskCardTitle)
        val tvTaskTime = taskView.findViewById<TextView>(R.id.tvTaskCardTime)
        val checkTaskDone = taskView.findViewById<CheckBox>(R.id.checkTaskDone)

        tvTaskTitle.text = task.name
        tvTaskTime.text = "${task.date}  ${task.time}"
        checkTaskDone.isChecked = true
        checkTaskDone.isEnabled = true

        taskView.setBackgroundColor(Color.parseColor("#E8F5E9"))
        tvTaskTitle.setTextColor(Color.parseColor("#006600"))
        tvTaskTime.setTextColor(Color.parseColor("#006600"))

        checkTaskDone.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
            TaskDatabaseHelper(this).updateTask(task)
            if (!isChecked) {
                completedContainer.removeView(taskView)
                addTaskView(task)
            }
        }
        completedContainer.addView(taskView)
    }

    private fun addOverdueTaskView(task: TaskModel) {
        val taskView = layoutInflater.inflate(R.layout.item_task_card, overdueContainer, false)
        val tvTaskTitle = taskView.findViewById<TextView>(R.id.tvTaskCardTitle)
        val tvTaskTime = taskView.findViewById<TextView>(R.id.tvTaskCardTime)
        val checkTaskDone = taskView.findViewById<CheckBox>(R.id.checkTaskDone)

        tvTaskTitle.text = task.name
        tvTaskTime.text = "${task.date}  ${task.time}"
        checkTaskDone.isChecked = false
        checkTaskDone.isEnabled = false
        taskView.setBackgroundColor(Color.parseColor("#F0F0F0"))
        tvTaskTitle.setTextColor(Color.parseColor("#555555"))
        tvTaskTime.setTextColor(Color.parseColor("#555555"))

        overdueContainer.addView(taskView)
    }
}
