package com.doseyenc.taskbuddy.presentation

import android.os.Bundle
import android.util.Log
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
            viewModel.initialize()
        }
        viewModel.tasks.observe(this) { taskList ->
            Log.e("task list", taskList.toString())
        }

        viewModel.loading.observe(this) { isLoading ->
        }

        viewModel.error.observe(this) { errorMsg ->
        }
    }
}
