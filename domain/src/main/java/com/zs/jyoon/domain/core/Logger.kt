package com.zs.jyoon.domain.core

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
}