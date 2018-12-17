@file:Suppress("unused")

package kotlincommon

import kotlincommon.Durations.callbackByKey
import kotlincommon.Durations.durationByKey
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

inline fun <T> measureDuration(
    onMeasurement: (duration: Duration) -> Unit = { println(it) },
    f: () -> T
): T {
    val start = System.nanoTime()
    val result = f()
    onMeasurement(Duration.ofNanos(System.nanoTime() - start))
    return result
}

inline fun <T> measureDuration(
    key: String,
    noinline onMeasurement: (key: String, duration: Duration) -> Unit = { _, duration -> println("$key: $duration") },
    f: () -> T
): T {
    val start = System.nanoTime()
    val result = f()
    val duration = Duration.ofNanos(System.nanoTime() - start)
    durationByKey[key] = durationByKey.getOrDefault(key, Duration.ZERO) + duration
    callbackByKey[key] = onMeasurement
    return result
}

object Durations {
    val durationByKey: MutableMap<String, Duration> = ConcurrentHashMap()
    val callbackByKey: MutableMap<String, (key: String, duration: Duration) -> Unit> = ConcurrentHashMap()

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            callbackByKey.forEach { key, callback ->
                callback(key, durationByKey[key]!!)
            }
        })
    }
}
