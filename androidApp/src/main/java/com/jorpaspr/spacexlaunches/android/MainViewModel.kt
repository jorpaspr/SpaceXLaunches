package com.jorpaspr.spacexlaunches.android

import android.app.Application
import androidx.lifecycle.*
import com.jorpaspr.spacexlaunches.SpaceXSdk
import com.jorpaspr.spacexlaunches.android.livedata.SingleLiveEvent
import com.jorpaspr.spacexlaunches.cache.DatabaseDriverFactory
import com.jorpaspr.spacexlaunches.entity.RocketLaunch
import kotlinx.coroutines.launch

class MainViewModel(
    private val sdk: SpaceXSdk
) : ViewModel() {
    private val _launches = MutableLiveData<List<RocketLaunch>>(emptyList())
    val launches: LiveData<List<RocketLaunch>> = _launches

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _refreshing = MutableLiveData(false)
    val refreshing: MutableLiveData<Boolean> = _refreshing

    private val _error = SingleLiveEvent<String>()
    val error: LiveData<String> = _error

    init {
        _loading.value = true
        displayLaunches(needReload = false)
    }

    private fun displayLaunches(needReload: Boolean) {
        viewModelScope.launch {
            runCatching {
                sdk.getLaunches(forceReload = needReload)
            }.onSuccess {
                _launches.value = it
            }.onFailure {
                _error.value = it.localizedMessage
            }
            _loading.value = false
            refreshing.value = false
        }
    }

    fun refresh() {
        refreshing.value = true
        displayLaunches(needReload = true)
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(SpaceXSdk(DatabaseDriverFactory(application))) as T
        }
    }
}
