package com.jorpaspr.spacexlaunches.android.livedata

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData

@Composable
fun <T> LiveData<T>.observeAsStateNotNull(): State<T> = observeAsState(requireNotNull(value))
