package com.jorpaspr.spacexlaunches

import com.jorpaspr.spacexlaunches.cache.Database
import com.jorpaspr.spacexlaunches.cache.DatabaseDriverFactory
import com.jorpaspr.spacexlaunches.entity.RocketLaunch
import com.jorpaspr.spacexlaunches.network.SpaceXApi

class SpaceXSdk(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}
