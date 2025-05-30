package com.example.bryjchallenge.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor() {
    fun provideList(): Result<List<String>> {
        val list: MutableList<String> = mutableListOf()
        (1..30).forEach { idx ->
            list.add("Element $idx")
        }
        return Result.Success(list.toList())
    }

    fun provideErrorList(): Result<Nothing> {
        return Result.Error(ArrayIndexOutOfBoundsException("Error message"))
    }
}