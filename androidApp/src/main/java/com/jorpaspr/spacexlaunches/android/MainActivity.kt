package com.jorpaspr.spacexlaunches.android

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.isVisible
import com.google.accompanist.themeadapter.material.MdcTheme

class MainActivity : AppCompatActivity() {
    private val progressBarView: FrameLayout by lazy { findViewById(R.id.progressBar) }

    private val composeView: ComposeView by lazy { findViewById(R.id.composeView) }

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "SpaceX Launches"
        setContentView(R.layout.activity_main)

        composeView.setContent {
            MdcTheme {
                LaunchList(viewModel)
            }
        }

        viewModel.loading.observe(this) { loading ->
            progressBarView.isVisible = loading
        }

        viewModel.error.observe(this) { message ->
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}
