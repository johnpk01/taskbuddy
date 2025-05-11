package com.doseyenc.taskbuddy.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.doseyenc.taskbuddy.domain.repository.TaskRepository
import com.doseyenc.taskbuddy.utils.TokenManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TaskSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val taskRepository: TaskRepository,
    private val tokenManager: TokenManager
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val token = tokenManager.getAccessToken()
            if (token.isNullOrBlank()) return Result.retry()

            val tasks = taskRepository.getTasksFromApi()
            taskRepository.clearTasksInDb()
            taskRepository.saveTasksToDb(tasks)

            Result.success()
        } catch (e: Exception) {
            Log.e("TaskSyncWorker", "Error in doWork: ${e.localizedMessage}", e)
            Result.retry()
        }
    }

    @dagger.assisted.AssistedFactory
    interface Factory : ChildWorkerFactory {
        override fun create(appContext: Context, workerParams: WorkerParameters): TaskSyncWorker
    }
}