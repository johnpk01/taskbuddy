package com.doseyenc.taskbuddy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.doseyenc.taskbuddy.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}