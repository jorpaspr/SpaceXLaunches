package com.jorpaspr.spacexlaunches.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jorpaspr.spacexlaunches.entity.RocketLaunch

class MainViewModel : ViewModel() {
    var launches = MutableLiveData<List<RocketLaunch>>(emptyList())

    var refreshing = MutableLiveData(false)

    fun refresh() {
        refreshing.value = true
    }
}
