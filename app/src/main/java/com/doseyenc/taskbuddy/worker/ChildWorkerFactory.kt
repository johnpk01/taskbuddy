package com.doseyenc.taskbuddy.worker


import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {
    fun create(appContext: Context, workerParams: WorkerParameters): ListenableWorker
}