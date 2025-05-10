package com.doseyenc.taskbuddy.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.doseyenc.taskbuddy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setupViewModel()
        setContentView(binding.root)
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.fetchTasks()
        }
        viewModel.tasks.observe(this) { taskList ->
        }

        viewModel.loading.observe(this) { isLoading ->
        }

        viewModel.error.observe(this) { errorMsg ->
        }
    }
}
