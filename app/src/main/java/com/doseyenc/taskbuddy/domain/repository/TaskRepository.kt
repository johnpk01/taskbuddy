package com.doseyenc.taskbuddy.domain.repository

import com.doseyenc.taskbuddy.data.model.Task

interface TaskRepository {
    suspend fun getTasksFromApi(): List<Task>
    suspend fun getTasksFromDb(): List<Task>
    suspend fun saveTasksToDb(tasks: List<Task>)
    suspend fun clearTasksInDb()
    suspend fun searchTasks(query: String): List<Task>
}