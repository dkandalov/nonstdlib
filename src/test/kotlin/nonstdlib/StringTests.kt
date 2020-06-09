package nonstdlib

import datsok.shouldEqual
import org.junit.Test

class StringTests {
    @Test fun `it works`() {
        "abcde".size shouldEqual 5
        "abcde".tail() shouldEqual "bcde"
        "abcde" * 2 shouldEqual "abcdeabcde"
    }
}