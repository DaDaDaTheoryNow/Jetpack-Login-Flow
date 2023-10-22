package com.dadadadev.loginflow.data.service.time

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TimeServiceInterface {
    fun getCurrentTime(): Flow<Date>
}