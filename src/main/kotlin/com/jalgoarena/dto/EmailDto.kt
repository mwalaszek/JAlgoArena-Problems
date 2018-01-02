package com.jalgoarena.dto

data class EmailDto (
        val receiver: String,
        var subject: String,
        val message: String
)