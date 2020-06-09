package nonstdlib

import java.io.*
import java.net.ServerSocket
import java.nio.charset.Charset
import java.util.*
import kotlin.text.Charsets.UTF_8


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

inline fun <T> T.with(block: (T) -> Unit): T = also(block)

/**
 * Forces `when` to be exhaustive when it's not assigned to any value.
 * For example:
 * ```
 * enum class Foo { A, B }
 * when (foo) {
 *   A -> // ...
 * }.exhaustive // will enforce compilation error
 * ```
 */
@Suppress("unused")
val Unit.exhaustive get() = this

fun Throwable.toStringStacktrace() = this.let {
    val stringWriter = StringWriter()
    it.printStackTrace(PrintWriter(stringWriter))
    stringWriter.toString()
}

@Suppress("unused")
inline fun <reified T> T.resourceStream(path: String): InputStream =
    T::class.java.getResourceAsStream(path)

inline fun <reified T> T.resourceReader(path: String, charset: Charset = UTF_8): InputStreamReader =
    resourceStream(path).reader(charset)

@Suppress("unused")
fun findFreePort(): Int {
    try {
        ServerSocket(0).use { socket -> return socket.localPort }
    } catch (e: IOException) {
        error("Failure to find free port")
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
