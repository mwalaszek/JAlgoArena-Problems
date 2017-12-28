package com.jalgoarena.service

import com.jalgoarena.data.ProblemsRepository
import com.jalgoarena.domain.Problem
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import java.util.*


class DailyProblemServiceTest {

    private lateinit var problemsRepository: ProblemsRepository
    private lateinit var mailingClient: MailingClient

    @Before
    fun setUp(){
        problemsRepository = mock(ProblemsRepository::class.java)
        mailingClient = mock(MailingClient::class.java)
    }

    @Test
    fun shouldNotSendEmailIfRepositoryEmpty(){
        given(problemsRepository.findAll()).willReturn(Collections.emptyList())
        val dailyProblemsService = DailyProblemService(problemsRepository)

        dailyProblemsService.sendDailyProblem()

        verify(problemsRepository).findAll()
        verify(mailingClient, never()).sendEmail()
    }

    @Test
    fun shouldSendEmailIfRepositoryNotEmpty(){
        given(problemsRepository.findAll()).willReturn(listOf(
                Problem("0", "problem 1", "problem 1", 10, null, emptyList(), 1),
                Problem("1", "problem 2", "problem 2", 10, null, emptyList(), 1)
        ))
        val dailyProblemsService = DailyProblemService(problemsRepository)

        dailyProblemsService.sendDailyProblem()

        verify(problemsRepository).findAll()
        verify(mailingClient, Mockito.atLeastOnce()).sendEmail()
    }
}