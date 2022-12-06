package com.jorpaspr.spacexlaunches.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.jorpaspr.spacexlaunches.android.theme.SpaceXLaunchesTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SpaceXLaunchesTheme {
                Scaffold(
                    modifier = Modifier.statusBarsPadding(),
                    topBar = { TopAppBar(title = { Text(getString(R.string.app_name)) }) },
                ) { contentPadding ->
                    MainScreen(Modifier.padding(contentPadding), viewModel)
                }
            }
        }

        viewModel.error.observe(this) { message ->
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}
