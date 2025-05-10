package com.doseyenc.taskbuddy.di

import com.doseyenc.taskbuddy.data.local.TaskDao
import com.doseyenc.taskbuddy.data.remote.TaskApiService
import com.doseyenc.taskbuddy.domain.repository.TaskRepository
import com.doseyenc.taskbuddy.domain.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskApiService: TaskApiService,
        taskDao: TaskDao
    ): TaskRepository {
        return TaskRepositoryImpl(taskApiService, taskDao)
    }
}