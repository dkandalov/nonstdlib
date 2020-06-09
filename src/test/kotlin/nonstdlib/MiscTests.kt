package nonstdlib

import datsok.shouldEqual
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class MiscTests {
    @Test fun `printed works`() = SystemOutFixture().run { out ->
        123.printed() shouldEqual 123
        234.printed(prefix = "value=") shouldEqual 234
        345.printedAs { (it + 5).toString() } shouldEqual 345

        out.toString() shouldEqual
            "123\n" +
            "value=234\n" +
            "350\n"
    }

    @Test fun `println with varargs`() = SystemOutFixture().run { out ->
        println(1, 2, 3, 4, 5)
        out.toString() shouldEqual "1, 2, 3, 4, 5\n"
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