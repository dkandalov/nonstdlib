@file:Suppress("NOTHING_TO_INLINE")

package nonstdlib

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.util.ArrayList
import kotlin.math.pow
import kotlin.random.Random
import kotlin.random.nextInt


fun Int.pow(n: Int): Int = this.toDouble().pow(n.toDouble()).toInt()

fun Long.pow(n: Long): Long = this.toDouble().pow(n.toDouble()).toLong()

tailrec fun Int.intFactorial(result: Int = 1): Int =
    if (this <= 1) result
    else (this - 1).intFactorial(result * this)

tailrec fun Int.longFactorial(result: Long = 1): Long =
    if (this <= 1) result
    else (this - 1).longFactorial(result * this)

fun Int.factorial(): BigInteger = BigInteger(this.toString()).factorial()

tailrec fun BigInteger.factorial(result: BigInteger = ONE): BigInteger =
    if (this <= ONE) result
    else (this - ONE).factorial(result * this)


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

fun Byte.toBinaryString(): String = Integer.toBinaryString(this.toInt().and(0xFF)).padStart(8, '0')

fun Short.toBinaryString(): String = Integer.toBinaryString(this.toInt().and(0xFFFF)).padStart(16, '0')

fun Int.toBinaryString(): String = Integer.toBinaryString(this).padStart(32, '0')
