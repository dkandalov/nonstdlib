package nonstdlib

val String.size: Int get() = length

fun String.tail(): String = drop(1)

operator fun String.times(size: Int): String = 0.until(size).joinBy("") { this }

fun Int.toBinaryString(): String = Integer.toBinaryString(this).padStart(32, '0')

fun ByteArray.toBinaryString(): String =
    joinToString("") { Integer.toBinaryString(it.toInt().and(0xFF)).padStart(8, '0') }
