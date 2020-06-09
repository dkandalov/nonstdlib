package nonstdlib

import datsok.shouldEqual
import org.junit.Test
import kotlin.text.Charsets.UTF_8

class StringTests {
    @Test fun `it works`() {
        "".size shouldEqual 0
        "abcde".size shouldEqual 5

        "".tail() shouldEqual ""
        "abcde".tail() shouldEqual "bcde"

        "abcde" * 0 shouldEqual ""
        "abcde" * 1 shouldEqual "abcde"
        "abcde" * 2 shouldEqual "abcdeabcde"

        "".nullIfEmpty() shouldEqual null
        "abcde".nullIfEmpty() shouldEqual "abcde"

        42.formattedAs("%04d") shouldEqual "0042"

        "\n".normaliseLineBreaks() shouldEqual "\n"
        "foo\r\nbar\rbuzz\n".normaliseLineBreaks() shouldEqual "foo\nbar\nbuzz\n"

        "abcde".toInputStream().readAllBytes().toString(UTF_8) shouldEqual "abcde"
    }
}