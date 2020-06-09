package nonstdlib

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

val String.size: Int get() = length

fun String.tail(): String = drop(1)

operator fun String.times(size: Int): String = 0.until(size).joinBy("") { this }

fun Any.formattedAs(formatString: String) = formatString.format(this)

fun String.normaliseLineBreaks(): String = replace("\r\n", "\n").replace("\r", "\n")

fun String.toInputStream(charset: Charset = UTF_8): InputStream = ByteArrayInputStream(this.toByteArray(charset))
