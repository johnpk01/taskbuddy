package com.doseyenc.taskbuddy.di

import com.doseyenc.taskbuddy.worker.ChildWorkerFactory
import com.doseyenc.taskbuddy.worker.TaskSyncWorker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskSyncWorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(TaskSyncWorker::class)
    abstract fun bindTaskSyncWorkerFactory(factory: TaskSyncWorker.Factory): ChildWorkerFactory
}