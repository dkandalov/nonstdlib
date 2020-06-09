@file:Suppress("NOTHING_TO_INLINE")

package nonstdlib

import java.util.ArrayList

/**
 * Same as `joinToString` just a shorter name to type.
 */
fun <T> Iterable<T>.join(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "..."
): String {
    return joinBy(separator, prefix, postfix, limit, truncated, null)
}

/**
 * Same as [join] but has different name so it doesn't show up first in auto-complete,
 * see https://youtrack.jetbrains.com/issue/KT-30936
 */
fun <T> Iterable<T>.joinBy(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: ((T) -> CharSequence)?
): String {
    return joinToString(separator, prefix, postfix, limit, truncated, transform)
}

fun <T> Iterable<T>.skip(n: Int): Iterable<T> {
    val iterator = iterator()
    repeat(times = n) {
        if (!iterator.hasNext()) throw IllegalStateException()
        iterator.next()
    }
    return object : Iterable<T> {
        override fun iterator() = iterator
    }
}

fun <T> Iterator<T>.toList() = asSequence().toList()

fun <T> List<T>.tail(): List<T> = drop(1)

fun <T, R> List<T>.chunkedBy(f: (T) -> R): List<List<T>> = asSequence().chunkedBy(f).toList()

fun <T, R> Iterator<T>.chunkedBy(f: (T) -> R): Iterator<List<T>> = asSequence().chunkedBy(f).iterator()

fun <T, R> Sequence<T>.chunkedBy(f: (T) -> R): Sequence<List<T>> = sequence {
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

inline fun <T : Number> Iterable<T>.sumAsLong(): Long =
    sumAsLongBy { it.toLong() }

inline fun <T> Iterable<T>.sumAsLongBy(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
