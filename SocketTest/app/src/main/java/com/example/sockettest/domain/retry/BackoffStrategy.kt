package com.example.sockettest.domain.retry

interface BackoffStrategy {
    fun getDelay(attempt: Int): Long? // Denemeye bağlı gecikme süresi
}