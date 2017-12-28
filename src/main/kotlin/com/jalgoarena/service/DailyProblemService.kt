package com.jalgoarena.service

import com.jalgoarena.data.ProblemsRepository
import javax.inject.Inject

class DailyProblemService (
        @Inject private val problemsRepository: ProblemsRepository
){
    fun sendDailyProblem() {

    }
}