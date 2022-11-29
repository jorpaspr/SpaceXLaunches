package com.jorpaspr.spacexlaunches.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jorpaspr.spacexlaunches.entity.RocketLaunch

class LaunchesRvAdapter(var launches: List<RocketLaunch>) :
    RecyclerView.Adapter<LaunchesRvAdapter.LaunchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launch, parent, false)
        return LaunchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bindData(launches[position])
    }

    override fun getItemCount(): Int = launches.count()

    class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val missionNameTextView = itemView.findViewById<TextView>(R.id.missionName)
        private val launchYearTextView = itemView.findViewById<TextView>(R.id.launchYear)
        private val launchSuccessTextView = itemView.findViewById<TextView>(R.id.launchSuccess)
        private val missionDetailsTextView = itemView.findViewById<TextView>(R.id.details)

        fun bindData(launch: RocketLaunch) = with(itemView.context) {
            missionNameTextView.text = getString(R.string.mission_name_field, launch.missionName)
            launchYearTextView.text =
                getString(R.string.launch_year_field, launch.launchYear.toString())
            missionDetailsTextView.text = getString(R.string.details_field, launch.details ?: "")
            val launchSuccess = launch.launchSuccess
            if (launchSuccess != null) {
                if (launchSuccess) {
                    launchSuccessTextView.text = getString(R.string.successful)
                    launchSuccessTextView.setTextColor(getColor(R.color.colorSuccessful))
                } else {
                    launchSuccessTextView.text = getString(R.string.unsuccessful)
                    launchSuccessTextView.setTextColor(getColor(R.color.colorUnsuccessful))
                }
            } else {
                launchSuccessTextView.text = getString(R.string.no_data)
                launchSuccessTextView.setTextColor(getColor(R.color.colorNoData))
            }
        }
    }
}
