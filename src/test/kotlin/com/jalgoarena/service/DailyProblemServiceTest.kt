package com.jalgoarena.service

import com.jalgoarena.data.ProblemsRepository
import com.jalgoarena.domain.Problem
import com.jalgoarena.domain.User
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DailyProblemServiceTest {

    @Mock
    private lateinit var problemsRepository: ProblemsRepository
    @Mock
    private lateinit var mailingClient: Mailing
    @Mock
    private lateinit var usersClient: Users

    @InjectMocks
    private lateinit var dailyProblemService: DailyProblemService

    @Test
    fun shouldNotSendEmailIfRepositoryEmpty(){
        given(problemsRepository.findAll()).willReturn(Collections.emptyList())

        dailyProblemService.sendDailyProblem()

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

        dailyProblemService.sendDailyProblem()

        verify(problemsRepository).findAll()
        verify(mailingClient, times(3))
                .sendEmail(Matchers.anyString(), Matchers.matches("Daily problem from JAlgoArena"),
                        Matchers.anyString())
    }
}