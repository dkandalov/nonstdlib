@file:Suppress("unused")

package kotlincommon

import java.time.Duration
import java.time.temporal.ChronoUnit


fun <T> T.printed(f: (T) -> String = { it.toString() }): T {
    println(f(this))
    return this
}

fun <T> Iterable<T>.join(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: ((T) -> CharSequence)? = null
): String {
    return this.joinToString(separator, prefix, postfix, limit, truncated, transform)
}

fun <E> Iterable<E>.skip(n: Int): Iterable<E> {
    val iterator = iterator()
    0.until(n).forEach {
        if (!iterator.hasNext()) throw IllegalStateException()
        iterator.next()
    }
    return object: Iterable<E> {
        override fun iterator() = iterator
    }
}

fun <E> List<E>.sliding(windowSize: Int): List<List<E>> {
    return (0..(size - windowSize)).map { subList(it, it + windowSize) }
}

fun <E> Iterable<E>.tail() = drop(1)

fun String.tail() = drop(1)

operator fun String.times(size: Int) = 0.until(size).map { this }.joinToString("")

fun <E> List<E>.permutations(): List<List<E>> =
    if (size <= 1) listOf(this)
    else flatMap { item ->
        (this - item).permutations().map { it: List<E> -> listOf(item) + it }
    }.distinct()

fun measureTimeMillis(block: () -> Unit): Duration {
    val millis = kotlin.system.measureTimeMillis(block)
    return Duration.of(millis, ChronoUnit.MILLIS)
}

fun byteArray(vararg bytes: Byte): ByteArray = bytes

fun byteList(vararg bytes: Byte): List<Byte> = bytes.toList()

private fun Int.toBinaryString() = Integer.toBinaryString(this).padStart(32, '0')

private fun ByteArray.toBinaryString() = this
    .map { Integer.toBinaryString(it.toInt().and(0xFF)).padStart(8, '0') }
    .joinToString("")