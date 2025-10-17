package com.example.taskscheduler

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import com.example.taskscheduler.model.TaskModel
import com.example.taskscheduler.data.TaskDatabaseHelper
import android.content.Intent

class CreateTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etTaskName = findViewById<EditText>(R.id.etTaskName)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etTime = findViewById<EditText>(R.id.etTime)
        val btnCalendar = findViewById<ImageView>(R.id.btnCalendar)
        val btnTime = findViewById<ImageView>(R.id.btnTime)
        val switchReminder = findViewById<Switch>(R.id.switchReminder)
        val chipLife = findViewById<TextView>(R.id.chipLife)
        val chipWork = findViewById<TextView>(R.id.chipWork)
        val chipStudy = findViewById<TextView>(R.id.chipStudy)
        val btnCreate = findViewById<Button>(R.id.btnCreate)

        btnBack.setOnClickListener {
            finish()
        }
        //Ognoo songoh
        btnCalendar.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, { _, y, m, d ->
                etDate.setText("$d/${m + 1}/$y")
            }, year, month, day).show()
        }
        //Tsag songoh
        btnTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            TimePickerDialog(this, { _, h, m ->
                etTime.setText(String.format("%02d:%02d", h, m))
            }, hour, minute, true).show()
        }
        //Reminder Switch
        switchReminder.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this,
                if (isChecked) "Reminder ON" else "Reminder OFF",
                Toast.LENGTH_SHORT
            ).show()
        }
        //Category songoh
        var selectedCategory = ""
        val resetChips = {
            chipLife.setBackgroundResource(R.drawable.card_bg)
            chipWork.setBackgroundResource(R.drawable.card_bg)
            chipStudy.setBackgroundResource(R.drawable.card_bg)
        }
        chipLife.setOnClickListener {
            resetChips()
            chipLife.setBackgroundResource(R.drawable.fab_bg)
            selectedCategory = "Life"
        }
        chipWork.setOnClickListener {
            resetChips()
            chipWork.setBackgroundResource(R.drawable.fab_bg)
            selectedCategory = "Work"
        }
        chipStudy.setOnClickListener {
            resetChips()
            chipStudy.setBackgroundResource(R.drawable.fab_bg)
            selectedCategory = "Study"
        }

        btnCreate.setOnClickListener {
            val taskName = etTaskName.text.toString().trim()
            val date = etDate.text.toString().trim()
            val time = etTime.text.toString().trim()
            if (taskName.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Бүх талбарыг бөглөнө үү!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val task = TaskModel(
                name = taskName,
                date = date,
                time = time,
                category = selectedCategory,
                reminder = switchReminder.isChecked
            )
            val db = TaskDatabaseHelper(this)
            db.addTask(task)
            Toast.makeText(
                this,
                "Task Created:\n$taskName\n$date $time\nCategory: $selectedCategory",
                Toast.LENGTH_LONG
            ).show()
            //hadgalaad home shiljine
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
