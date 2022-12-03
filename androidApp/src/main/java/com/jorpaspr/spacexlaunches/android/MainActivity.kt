package com.jorpaspr.spacexlaunches.android

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.themeadapter.material.MdcTheme
import com.jorpaspr.spacexlaunches.SpaceXSdk
import com.jorpaspr.spacexlaunches.cache.DatabaseDriverFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val progressBarView: FrameLayout by lazy { findViewById(R.id.progressBar) }

    private val sdk = SpaceXSdk(DatabaseDriverFactory(this))

    private val composeView: ComposeView by lazy { findViewById(R.id.composeView) }

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "SpaceX Launches"
        setContentView(R.layout.activity_main)

        composeView.setContent {
            MdcTheme {
                LaunchList()
            }
        }

        viewModel.refreshing.observe(this) { refreshing ->
            if (refreshing) {
                displayLaunches(true)
            }
        }

        displayLaunches(false)
    }

    private fun displayLaunches(needReload: Boolean) {
        progressBarView.isVisible = !needReload
        lifecycleScope.launch {
            runCatching {
                sdk.getLaunches(needReload)
            }.onSuccess {
                viewModel.launches.value = it
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            progressBarView.isVisible = false
            viewModel.refreshing.value = false
        }
    }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}
