@file:Suppress("unused", "NOTHING_TO_INLINE")

package kotlincommon

import java.math.BigInteger
import java.util.*
import kotlin.math.pow
import kotlin.random.Random
import kotlin.random.nextInt

val String.size: Int get() = length

fun <T> T.printed(prefix: String = "", f: (T) -> String = { it.toPrintableString() }): T {
    println(prefix + f(this))
    return this
}

fun println(first: Any, second: Any, vararg rest: Any) {
    println((listOf(first, second) + rest).joinToString(", ") { it.toPrintableString() })
}

inline fun <T, R> T.ifNotNull(f: (T) -> R) = this?.let(f)

inline fun <T> Iterable<T>.doesNotContain(element: T): Boolean = !contains(element)

inline fun <K, V> Map<K, V>.doesNotContain(key: K): Boolean = !contains(key)

fun <T> Array<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun <T> Iterable<T>.join(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: ((T) -> CharSequence)? = null
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

fun <T> Iterable<T>.tail(): Iterable<T> = drop(1)

fun <T> List<T>.tail(): List<T> = drop(1)

fun String.tail(): String = drop(1)

operator fun String.times(size: Int): String = 0.until(size).join("") { this }


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

fun byteArray(vararg bytes: Byte): ByteArray = bytes

fun byteList(vararg bytes: Byte): List<Byte> = bytes.toList()

fun Int.toBinaryString(): String = Integer.toBinaryString(this).padStart(32, '0')

fun ByteArray.toBinaryString(): String =
    joinToString("") { Integer.toBinaryString(it.toInt().and(0xFF)).padStart(8, '0') }

fun Int.pow(n: Int): Int = this.toDouble().pow(n.toDouble()).toInt()

fun Long.pow(n: Long): Long = this.toDouble().pow(n.toDouble()).toLong()

tailrec fun Int.intFactorial(result: Int = 1): Int =
    if (this <= 1) result
    else (this - 1).intFactorial(result * this)

tailrec fun Int.longFactorial(result: Long = 1): Long =
    if (this <= 1) result
    else (this - 1).longFactorial(result * this)

fun Int.factorial(): BigInteger = BigInteger(this.toString()).factorial()

tailrec fun BigInteger.factorial(result: BigInteger = BigInteger.ONE): BigInteger =
    if (this <= BigInteger.ONE) result
    else (this - BigInteger.ONE).factorial(result * this)

fun Random.listOfInts(
    size: Int = -1,
    sizeRange: IntRange = IntRange.EMPTY,
    valuesRange: IntRange = IntRange(Int.MIN_VALUE, Int.MAX_VALUE)
): List<Int> {
    require(size != -1 || !sizeRange.isEmpty()) { "`size` or `sizeRange` must be specified (but not both)" }
    val result = ArrayList<Int>()
    var i = if (size != -1) size else nextInt(sizeRange)
    while (i-- > 0) {
        result.add(nextInt(valuesRange))
    }
    return result
}

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

inline fun <E> Iterable<E>.permutations(): List<List<E>> = toList().permutationsSequence().toList()

inline fun <E> List<E>.permutations(): List<List<E>> = permutationsSequence().toList()

/**
 * https://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order
 * https://www.nayuki.io/page/next-lexicographical-permutation-algorithm
 */
fun <E> List<E>.permutationsSequence(): Sequence<List<E>> = sequence {
    val values = this@permutationsSequence
    val list = indices.toMutableList()
    yield(list.map { values[it] })

    while (true) {
        val i = list.indices.windowed(2).findLast { (i1, i2) -> list[i1] < list[i2] }?.first() ?: break
        val j = IntRange(i + 1, list.size - 1).findLast { list[i] < list[it] }!!
        list.swap(i, j)
        list.subList(i + 1, list.size).reverse()
        yield(list.map { values[it] })
    }
}

private fun Any?.toPrintableString(): String =
    when (this) {
        is Array<*>     -> Arrays.toString(this)
        is BooleanArray -> Arrays.toString(this)
        is ByteArray    -> Arrays.toString(this)
        is CharArray    -> Arrays.toString(this)
        is ShortArray   -> Arrays.toString(this)
        is IntArray     -> Arrays.toString(this)
        is LongArray    -> Arrays.toString(this)
        is FloatArray   -> Arrays.toString(this)
        is DoubleArray  -> Arrays.toString(this)
        else            -> this.toString()
    }
