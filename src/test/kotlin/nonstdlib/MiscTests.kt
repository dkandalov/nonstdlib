package nonstdlib

import datsok.shouldEqual
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MiscTests {
    @Test fun `printed works`() = SystemOutFixture().run { out ->
        123.printed() shouldEqual 123
        234.printed(prefix = "value=") shouldEqual 234
        listOf(1, 2, 3).printedAs { it.join(";") } shouldEqual listOf(1, 2, 3)

        out.toString() shouldEqual
            "123\n" +
            "value=234\n" +
            "1;2;3\n"
    }

    @Test fun `println with varargs`() = SystemOutFixture().run { out ->
        println(1, 2, 3, 4, 5)
        out.toString() shouldEqual "1, 2, 3, 4, 5\n"
    }

    @Test fun `exception stacktrace as a string`() {
        val stacktrace = Exception("foo").toStringStacktrace()
            .lines().take(2).map { it.replace(Regex("MiscTests.kt:\\d+"), "...") }

        stacktrace shouldEqual listOf(
            "java.lang.Exception: foo",
            "\tat nonstdlib.MiscTests.exception stacktrace as a string(...)"
        )
    }

    @Test fun `reading resource`() {
        resourceStream("/nonstdlib/MiscKt.class")?.readAllBytes()?.isNotEmpty() shouldEqual true
        resourceReader("/nonstdlib/MiscKt.class")?.readLines()?.isNotEmpty() shouldEqual true
    }

    private class SystemOutFixture {
        fun run(f: (ByteArrayOutputStream) -> Unit) {
            val originalOut = System.out
            try {
                val out = ByteArrayOutputStream()
                System.setOut(PrintStream(out))
                f(out)
            } finally {
                System.setOut(originalOut)
            }
        }
    }
}