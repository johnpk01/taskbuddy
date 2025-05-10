package com.doseyenc.taskbuddy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doseyenc.taskbuddy.data.model.Task
import com.doseyenc.taskbuddy.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchTasks() {
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val apiTasks = repository.getTasksFromApi()
                repository.clearTasksInDb()
                repository.saveTasksToDb(apiTasks)
                _tasks.value = repository.getTasksFromDb()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}