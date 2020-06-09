package nonstdlib

val String.size: Int get() = length

fun String.tail(): String = drop(1)

operator fun String.times(size: Int): String = 0.until(size).joinBy("") { this }
