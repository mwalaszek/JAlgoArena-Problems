package com.jalgoarena.service

import com.jalgoarena.data.ProblemsRepository
import com.jalgoarena.domain.Problem
import com.jalgoarena.domain.User
import java.util.*
import javax.inject.Inject

class DailyProblemService (
        @Inject private val problemsRepository: ProblemsRepository,
        @Inject private val usersClient: Users,
        @Inject private val mailingClient: MailingClient
){

    private val SUBJECT: String = "Daily problem from JAlgoArena"
    private val MESSAGE_TEMPLATE: String = "Hi %s, today's daily problem is %s."


    fun sendDailyProblem() {
        val problems = problemsRepository.findAll()
        if (problems.isNotEmpty()){
            val id = getProblemId(problems.size)
            sendEmails(problems.get(id))
        }
    }

    private fun sendEmails(problem: Problem) {
        val users = usersClient.findAllUsers()
        users.forEach {
            user -> mailingClient.sendDailyProblemEmail(
                user.email, SUBJECT,
                prepareMessage(user, problem))
        }
    }

    private fun prepareMessage(user: User, problem: Problem): String {
        return String.format(MESSAGE_TEMPLATE, user.username, problem.title)
    }

    private fun getProblemId(listSize: Int): Int = Random().nextInt(listSize)
}