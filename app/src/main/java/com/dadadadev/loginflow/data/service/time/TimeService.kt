package com.dadadadev.loginflow.data.service.time

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.Date

class TimeService : TimeServiceInterface {
    override fun getCurrentTime(): Flow<Date> = flow {
        while (true) {
            val currentTime = Calendar.getInstance().time
            emit(currentTime)
            delay(900L)
        }
    }
}