package com.doseyenc.taskbuddy.domain.repository

import com.doseyenc.taskbuddy.data.local.TaskDao
import com.doseyenc.taskbuddy.data.model.Task
import com.doseyenc.taskbuddy.data.remote.TaskApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(
    private val taskApi: TaskApiService,
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun getTasksFromApi(): List<Task> = withContext(Dispatchers.IO) {
        taskApi.getTasks()
    }

    override suspend fun saveTasksToDb(tasks: List<Task>) = withContext(Dispatchers.IO) {
        taskDao.insertTasks(tasks)
    }

    override suspend fun getTasksFromDb(): List<Task> = withContext(Dispatchers.IO) {
        taskDao.getAllTasks()
    }

    override suspend fun clearTasksInDb() = withContext(Dispatchers.IO) {
        taskDao.deleteAllTasks()
    }
}