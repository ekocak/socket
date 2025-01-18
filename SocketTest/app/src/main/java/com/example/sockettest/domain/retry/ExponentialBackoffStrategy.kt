package com.example.sockettest.domain.retry

class ExponentialBackoffStrategy(
    private val baseDelay: Long = 1000L,
    private val maxDelay: Long = 10000L
) : BackoffStrategy {
    init {
        require(baseDelay > 0) { "initialDurationMillis, $baseDelay, must be positive" }
        require(maxDelay > 0) { "maxDurationMillis, $maxDelay, must be positive" }
    }

    override fun getDelay(attempt: Int) =
        Math.min(
            maxDelay.toDouble(),
            baseDelay.toDouble() * Math.pow(2.0, attempt.toDouble())
        ).toLong()

}