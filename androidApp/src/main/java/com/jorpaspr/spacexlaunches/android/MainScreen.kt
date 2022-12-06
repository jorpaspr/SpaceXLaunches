package com.jorpaspr.spacexlaunches.android

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorpaspr.spacexlaunches.android.livedata.observeAsStateNotNull
import com.jorpaspr.spacexlaunches.entity.RocketLaunch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    val loading by mainViewModel.loading.observeAsStateNotNull()
    val refreshing by mainViewModel.refreshing.observeAsStateNotNull()
    val pullRefreshState =
        rememberPullRefreshState(refreshing = refreshing, onRefresh = mainViewModel::refresh)
    val launches by mainViewModel.launches.observeAsStateNotNull()

    Box(
        modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        if (!loading && !refreshing) {
            LaunchList(launches)
        }
        if (loading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun LaunchList(
    launches: List<RocketLaunch>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        items(
            items = launches
        ) { launch ->
            with(launch) {
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

@Composable
fun LaunchItem(
    missionName: String,
    isSuccess: Boolean?,
    launchYear: String,
    details: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(bottom = 16.dp)) {
            Text(
                text = stringResource(R.string.mission_name_field, missionName),
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
            Text(
                text = getLaunchSuccessText(isSuccess),
                color = getLaunchSuccessColour(isSuccess),
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.launch_year_field, launchYear),
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.details_field, details),
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun getLaunchSuccessText(isSuccess: Boolean?) = stringResource(
    when (isSuccess) {
        true -> R.string.successful
        false -> R.string.unsuccessful
        null -> R.string.no_data
    }
)

@Composable
private fun getLaunchSuccessColour(isSuccess: Boolean?) = colorResource(
    when (isSuccess) {
        true -> R.color.colorSuccessful
        false -> R.color.colorUnsuccessful
        null -> R.color.colorNoData
    }
)

@Preview
@Composable
fun ItemLaunchPreview() {
    LaunchItem(
        missionName = "Mission name",
        isSuccess = false,
        launchYear = "Launch year",
        details = "Details"
    )
}
