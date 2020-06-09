package nonstdlib

import java.util.*


fun <T> T.printed(prefix: String = ""): T = printedAs(prefix)

fun <T> T.printedAs(prefix: String = "", f: (T) -> String = { it.toPrintableString() }): T {
    println(prefix + f(this))
    return this
}

fun println(first: Any, second: Any, vararg rest: Any) {
    println((listOf(first, second) + rest).joinToString(", ") { it.toPrintableString() })
}

@Suppress("unused")
inline fun <T, R> T.ifNotNull(f: (T) -> R) = this?.let(f)


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
