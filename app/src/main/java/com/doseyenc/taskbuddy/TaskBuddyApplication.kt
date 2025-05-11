package com.doseyenc.taskbuddy

import android.app.Application
import androidx.work.Configuration
import com.doseyenc.taskbuddy.worker.TaskBuddyWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TaskBuddyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: TaskBuddyWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}