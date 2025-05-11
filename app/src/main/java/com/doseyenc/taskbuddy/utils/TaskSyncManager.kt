package com.doseyenc.taskbuddy.utils

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.doseyenc.taskbuddy.worker.TaskSyncWorker
import java.util.concurrent.TimeUnit

object TaskSyncManager {
    fun scheduleTaskSyncWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<TaskSyncWorker>(60, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "TaskSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}