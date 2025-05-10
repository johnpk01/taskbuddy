package com.doseyenc.taskbuddy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doseyenc.taskbuddy.data.model.LoginRequest
import com.doseyenc.taskbuddy.data.model.Task
import com.doseyenc.taskbuddy.domain.repository.TaskRepository
import com.doseyenc.taskbuddy.domain.usecase.LoginUseCase
import com.doseyenc.taskbuddy.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val tokenManager: TokenManager,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun initialize() {
        viewModelScope.launch {
            val token = tokenManager.getAccessToken()
            if (token.isNullOrEmpty()) {
                loginAndFetchTasks()
            } else {
                fetchTasks()
            }
        }
    }

    private suspend fun loginAndFetchTasks() {
        _loading.postValue(true)
        try {
            val result = loginUseCase(LoginRequest.build())
            tokenManager.saveTokens(
                accessToken = result.accessToken,
                refreshToken = result.refreshToken
            )
            fetchTasks()
        } catch (e: Exception) {
            _error.postValue("Login failed: ${e.message}")
        } finally {
            _loading.postValue(false)
        }
    }

    private suspend fun fetchTasks() {
        _loading.postValue(true)
        try {
            val apiTasks = repository.getTasksFromApi()
            repository.clearTasksInDb()
            repository.saveTasksToDb(apiTasks)
            _tasks.postValue(apiTasks)
        } catch (e: Exception) {
            _error.postValue(e.message)
        } finally {
            _loading.postValue(false)
        }
    }

    fun refresh() {
        initialize()
    }

    fun search(query: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _tasks.value = repository.searchTasks(query)
            } catch (e: Exception) {
                _error.value = "Search failed: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}