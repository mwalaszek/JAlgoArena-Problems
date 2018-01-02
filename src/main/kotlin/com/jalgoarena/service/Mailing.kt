package com.jalgoarena.service

interface Mailing {
    fun sendEmail(receiver: String, subject: String, message: String): Int
}