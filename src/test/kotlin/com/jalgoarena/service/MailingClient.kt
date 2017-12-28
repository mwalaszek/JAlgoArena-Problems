package com.jalgoarena.service

import com.netflix.discovery.EurekaClient
import org.springframework.stereotype.Service
import org.springframework.web.client.RestOperations
import javax.inject.Inject


@Service
open class MailingClient (
        @Inject private val discoveryClient: EurekaClient,
        @Inject private val restTemplate: RestOperations
) {

    private fun mailingServiceUrl(): String =
            discoveryClient
                    .getNextServerFromEureka("jalgo-mailing", false)
                    .homePageUrl


    fun sendEmail(): Int = 0
}