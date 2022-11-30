package com.jorpaspr.spacexlaunches.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LaunchItem(
    missionName: String,
    isSuccess: Boolean?,
    launchYear: String,
    details: String
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = stringResource(R.string.mission_name_field, missionName),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
            Text(
                text = getLaunchSuccessText(isSuccess),
                color = getLaunchSuccessColour(isSuccess),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.launch_year_field, launchYear),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.details_field, details),
                modifier = Modifier
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
