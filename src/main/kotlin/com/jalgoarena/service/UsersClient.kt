package com.jalgoarena.service

import com.jalgoarena.domain.User
import com.netflix.discovery.EurekaClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestOperations
import javax.inject.Inject

@Service
open class UsersClient(
        @Inject private val discoveryClient: EurekaClient,
        @Inject private val restTemplate : RestOperations
) {

    private val LOG = LoggerFactory.getLogger(this.javaClass)

    private fun authServiceUrl() =
            discoveryClient
                    .getNextServerFromEureka("jalgo-auth", false)
                    .homePageUrl


    fun findAllUsers() = handleExceptions(returnOnException = emptyList()) {
        restTemplate.getForObject(
                "${authServiceUrl()}/users", Array<User>::class.java)!!.asList()
    }



    private fun <T> handleExceptions(returnOnException: T, body: () -> T) = try {
        body()
    } catch(e: Exception) {
        LOG.error("Error in querying jalgoarena auth service", e)
        returnOnException
    }
}
