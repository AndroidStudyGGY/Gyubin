package com.gyub.implementationtestexample.xml

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gyub.implementationtestexample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *
 *
 * @author   Gyub
 * @created  2024/04/16
 */
class XmlMainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private val dataAdapter: DataAdapter by lazy { DataAdapter() }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@XmlMainActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainViewModel.fetchIssues("JakeWharton", "hugo")

        binding.recyclerView.run {
            adapter = dataAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.dataList.collectLatest {
                    dataAdapter.submitList(it)
                }
            }
        }
    }
}