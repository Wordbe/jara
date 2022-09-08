package co.wordbe.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Component
class CoroutineCacheManager<T> {

    private val localCache = ConcurrentHashMap<String, CacheWrapper<T>>()

    suspend fun awaitPut(key: String, value: T, ttl: Duration) {
        localCache[key] = CacheWrapper(value, Instant.now().plusMillis(ttl.toMillis()))
    }

    data class CacheWrapper<T>(val cached: T, val ttl: Instant)
}