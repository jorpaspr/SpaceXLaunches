package com.jorpaspr.spacexlaunches

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
