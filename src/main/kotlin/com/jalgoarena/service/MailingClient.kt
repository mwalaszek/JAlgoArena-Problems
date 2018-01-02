package com.jalgoarena.service

import com.jalgoarena.dto.EmailDto
import com.netflix.discovery.EurekaClient
import org.springframework.stereotype.Service
import org.springframework.web.client.RestOperations
import javax.inject.Inject


@Service
class MailingClient (
        @Inject private val discoveryClient: EurekaClient,
        @Inject private val restTemplate: RestOperations
) : Mailing {

    private fun mailingServiceUrl(): String =
            discoveryClient
                    .getNextServerFromEureka("jalgo-mailing", false)
                    .homePageUrl

    override fun sendEmail(receiver: String, subject: String, message: String): Int {
        val emailDto = EmailDto(receiver, subject, message)
        return restTemplate.postForObject(mailingServiceUrl() + "/sendEmail", emailDto, Int::class.java)
    }
}