package com.doseyenc.taskbuddy.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.doseyenc.taskbuddy.databinding.ActivityMainBinding
import com.doseyenc.taskbuddy.presentation.adapter.TaskAdapter
import com.google.android.material.snackbar.Snackbar
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter
    private var searchJob: Job? = null

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            binding.searchEditText.setText(result.contents)
            binding.searchEditText.clearFocus()
            viewModel.search(result.contents)

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        adapter = TaskAdapter()
        binding.taskRecyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.searchEditText.doAfterTextChanged { query ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.search(query.toString())
            }
        }

        binding.searchInputLayout.setEndIconOnClickListener {
            binding.searchEditText.text?.clear()
            binding.searchEditText.clearFocus()

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)

            viewModel.refresh()
        }

        binding.fabQr.setOnClickListener {
            val options = ScanOptions().apply {
                setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                setPrompt("Scan a QR Code")
                setBeepEnabled(true)
                setOrientationLocked(false)
                setCameraId(0)
            }
            barcodeLauncher.launch(options)
        }
    }

    private fun setupObservers() {
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
            binding.swipeRefresh.isRefreshing = isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}