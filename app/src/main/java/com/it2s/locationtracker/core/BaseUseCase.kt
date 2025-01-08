package com.it2s.locationtracker.core

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<T, P> {
    operator fun invoke(param: P): Flow<NetworkResult<T>>
}