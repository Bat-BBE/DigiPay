package com.example.taskscheduler.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.taskscheduler.model.TaskModel

class TaskDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "tasks.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_REMINDER = "reminder"
        private const val COLUMN_IS_DONE = "isDone"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_DATE TEXT,
                $COLUMN_TIME TEXT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_REMINDER INTEGER,
                $COLUMN_IS_DONE INTEGER DEFAULT 0
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addTask(task: TaskModel) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, task.name)
            put(COLUMN_DATE, task.date)
            put(COLUMN_TIME, task.time)
            put(COLUMN_CATEGORY, task.category)
            put(COLUMN_REMINDER, if (task.reminder) 1 else 0)
            put(COLUMN_IS_DONE, if (task.isDone) 1 else 0)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getAllTasks(): List<TaskModel> {
        val taskList = mutableListOf<TaskModel>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val task = TaskModel(
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                    reminder = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REMINDER)) == 1,
                    isDone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return taskList
    }
    fun updateTask(task: TaskModel) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, task.name)
            put(COLUMN_DATE, task.date)
            put(COLUMN_TIME, task.time)
            put(COLUMN_CATEGORY, task.category)
            put(COLUMN_REMINDER, if (task.reminder) 1 else 0)
            put(COLUMN_IS_DONE, if (task.isDone) 1 else 0)
        }
        db.update(
            TABLE_NAME,
            values,
            "$COLUMN_NAME = ? AND $COLUMN_DATE = ? AND $COLUMN_TIME = ?",
            arrayOf(task.name, task.date, task.time)
        )
        db.close()
    }
}
