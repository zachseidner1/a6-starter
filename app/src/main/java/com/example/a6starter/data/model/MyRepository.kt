package com.example.a6starter.data.model

import com.example.a6starter.data.remote.MyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(
    private val myApi: MyApi,
) {
    // TODO use the API as needed
}