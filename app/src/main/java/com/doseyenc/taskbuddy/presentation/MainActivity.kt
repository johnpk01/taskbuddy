package com.doseyenc.taskbuddy.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.doseyenc.taskbuddy.databinding.ActivityMainBinding
import com.doseyenc.taskbuddy.presentation.adapter.TaskAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setupView()
        setupViewModel()
        setContentView(binding.root)
    }

    private fun setupView() {
        adapter = TaskAdapter()
        binding.taskRecyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.initialize()
        }

        viewModel.tasks.observe(this) { taskList ->
            if (taskList.isNullOrEmpty()) {
                binding.taskRecyclerView.visibility = View.GONE
                binding.emptyLayout.visibility = View.VISIBLE
            } else {
                binding.taskRecyclerView.visibility = View.VISIBLE
                binding.emptyLayout.visibility = View.GONE
                adapter.submitList(taskList)
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            Log.e("loading", isLoading.toString())
            binding.swipeRefresh.isRefreshing = isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMsg ->
            Log.e("error", errorMsg.toString())
            errorMsg?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
