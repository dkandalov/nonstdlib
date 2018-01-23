@file:Suppress("unused")

package kotlincommon

import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.coroutines.experimental.buildSequence


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

fun <E> Iterable<E>.tail() = drop(1)

fun String.tail() = drop(1)

operator fun String.times(size: Int) = 0.until(size).map { this }.joinToString("")


fun <T, R> List<T>.chunkedBy(f: (T) -> R): List<List<T>> = asSequence().chunkedBy(f).toList()

fun <T, R> Iterator<T>.chunkedBy(f: (T) -> R): Iterator<List<T>> = asSequence().chunkedBy(f).iterator()

fun <T, R> Sequence<T>.chunkedBy(f: (T) -> R): Sequence<List<T>> = buildSequence {
    var lastKey: R? = null
    var list = ArrayList<T>()
    forEach { item ->
        val key = f(item)
        if (key != lastKey) {
            lastKey = key
            if (list.isNotEmpty()) yield(list)
            list = ArrayList()
        }
        list.add(item)
    }
    if (list.isNotEmpty()) yield(list)
}

fun <E> List<E>.permutations(): List<List<E>> =
    if (size <= 1) listOf(this)
    else flatMap { item ->
        (this - item).permutations().map { list -> listOf(item) + list }
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