package com.jalgoarena.service

import com.jalgoarena.data.ProblemsRepository
import com.jalgoarena.domain.Problem
import com.jalgoarena.domain.User
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Matchers
import org.mockito.Mockito.mock
import java.util.*


class DailyProblemServiceTest {

    private lateinit var problemsRepository: ProblemsRepository
    private lateinit var mailingClient: MailingClient
    private lateinit var usersClient: Users

    @Before
    fun setUp(){
        problemsRepository = mock(ProblemsRepository::class.java)
        usersClient = mock(Users::class.java)
        mailingClient = mock(MailingClient::class.java)
    }

    @Test
    fun shouldNotSendEmailIfRepositoryEmpty(){
        given(problemsRepository.findAll()).willReturn(Collections.emptyList())
        val dailyProblemsService = DailyProblemService(problemsRepository,
                usersClient, mailingClient)

        dailyProblemsService.sendDailyProblem()

        verify(problemsRepository).findAll()
        verifyZeroInteractions(mailingClient)
        verifyZeroInteractions(usersClient)
    }

    @Test
    fun shouldSendEmailIfRepositoryNotEmpty(){
        given(problemsRepository.findAll()).willReturn(listOf(
                Problem("0", "problem 1", "problem 1", 10, null, emptyList(), 1),
                Problem("1", "problem 2", "problem 2", 10, null, emptyList(), 1),
                Problem("2", "problem 3", "problem 3", 10, null, emptyList(), 1),
                Problem("3", "problem 4", "problem 4", 10, null, emptyList(), 1)

                ))
        given(usersClient.findAllUsers()).willReturn(listOf(
                User("usename1", "email1@jalgo.com"),
                User("usename2", "email2@jalgo.com"),
                User("usename3", "email3@jalgo.com")
        ))
        val dailyProblemsService = DailyProblemService(problemsRepository,
                usersClient, mailingClient)

        dailyProblemsService.sendDailyProblem()

        verify(problemsRepository).findAll()
        verify(mailingClient, times(3))
                .sendDailyProblemEmail(Matchers.matches("[a-z0-9]+@jalgo.com"), Matchers.matches("Daily problem from JAlgoArena"),
                        Matchers.matches("Hi username[0-9], today's daily problem is problem [0-9]."))
    }
}