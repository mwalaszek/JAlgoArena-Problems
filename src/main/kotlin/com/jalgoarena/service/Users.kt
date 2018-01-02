package com.jalgoarena.service

import com.jalgoarena.domain.User

interface Users {
    fun findAllUsers(): List<User>
}