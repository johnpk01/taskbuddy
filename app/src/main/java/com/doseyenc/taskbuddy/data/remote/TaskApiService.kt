package com.doseyenc.taskbuddy.data.remote

import com.doseyenc.taskbuddy.data.model.Task
import retrofit2.http.GET

interface TaskApiService {

    @GET("index.php/v1/tasks/select")
    suspend fun getTasks(): List<Task>
}