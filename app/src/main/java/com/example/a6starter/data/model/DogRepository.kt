package com.example.a6starter.data.model

import com.example.a6starter.data.entities.DogEntity
import com.example.a6starter.data.remote.DogBreedApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(
    private val dogBreedApi: DogBreedApi,
) {
    suspend fun getDogBreeds(pageNumber: Int): Response<DogEntity> {
        TODO(
            "Implement this function, all you need to do here is return" +
                    "the call from your API"
        )
    }
}