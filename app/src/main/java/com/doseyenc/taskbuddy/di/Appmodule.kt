package com.doseyenc.taskbuddy.di

import android.content.Context
import com.doseyenc.taskbuddy.data.local.TaskDao
import com.doseyenc.taskbuddy.data.remote.LoginApiService
import com.doseyenc.taskbuddy.data.remote.TaskApiService
import com.doseyenc.taskbuddy.domain.repository.LoginRepository
import com.doseyenc.taskbuddy.domain.repository.LoginRepositoryImpl
import com.doseyenc.taskbuddy.domain.repository.TaskRepository
import com.doseyenc.taskbuddy.domain.repository.TaskRepositoryImpl
import com.doseyenc.taskbuddy.domain.usecase.LoginUseCase
import com.doseyenc.taskbuddy.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideLoginRepository(
        loginApiService: LoginApiService
    ): LoginRepository {
        return LoginRepositoryImpl(loginApiService)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        loginRepository: LoginRepository
    ): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager {
        return TokenManager(context)
    }
}