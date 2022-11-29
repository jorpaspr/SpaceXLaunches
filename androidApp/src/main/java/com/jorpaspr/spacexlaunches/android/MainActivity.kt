package com.jorpaspr.spacexlaunches.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jorpaspr.spacexlaunches.SpaceXSdk
import com.jorpaspr.spacexlaunches.cache.DatabaseDriverFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val launchesRecyclerView: RecyclerView by lazy { findViewById(R.id.launchesListRv) }

    private val progressBarView: FrameLayout by lazy { findViewById(R.id.progressBar) }

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById(R.id.swipeContainer) }

    private val launchesRvAdapter = LaunchesRvAdapter(listOf())

    private val sdk = SpaceXSdk(DatabaseDriverFactory(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "SpaceX Launches"
        setContentView(R.layout.activity_main)
        launchesRecyclerView.adapter = launchesRvAdapter
        launchesRecyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            displayLaunches(true)
        }

        displayLaunches(false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayLaunches(needReload: Boolean) {
        progressBarView.isVisible = true
        lifecycleScope.launch {
            runCatching {
                sdk.getLaunches(needReload)
            }.onSuccess {
                launchesRvAdapter.launches = it
                launchesRvAdapter.notifyDataSetChanged()
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            progressBarView.isVisible = false
        }
    }
}
