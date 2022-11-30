package com.jorpaspr.spacexlaunches.android

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import com.jorpaspr.spacexlaunches.entity.RocketLaunch

class LaunchesRvAdapter(var launches: List<RocketLaunch>) :
    RecyclerView.Adapter<LaunchesRvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(launches[position])
    }

    override fun getItemCount(): Int = launches.count()

    class ViewHolder(private val composeView: ComposeView) : RecyclerView.ViewHolder(composeView) {
        init {
            composeView.setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }

        fun bindData(item: RocketLaunch) {
            composeView.setContent {
                with(item) {
                    LaunchItem(
                        missionName = missionName,
                        isSuccess = launchSuccess,
                        launchYear = launchYear.toString(),
                        details = details.orEmpty()
                    )
                }
            }
        }
    }
}
