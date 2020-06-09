package nonstdlib

import datsok.shouldEqual
import org.junit.Test

class CollectionsTests {
    @Test fun `iterator with skipped elements`() {
        listOf(1, 2, 3).skip(0).toList() shouldEqual listOf(1, 2, 3)
        listOf(1, 2, 3).skip(1).toList() shouldEqual listOf(2, 3)
        listOf(1, 2, 3).skip(2).toList() shouldEqual listOf(3)
        listOf(1, 2, 3).skip(3).toList() shouldEqual listOf()
    }

    @Test fun `chunk list according to selector function`() {
        fun isEven(n: Int) = n % 2 == 0

        listOf<Int>().chunkedBy(::isEven) shouldEqual listOf()
        listOf(1).chunkedBy(::isEven) shouldEqual listOf(listOf(1))
        listOf(1, 2).chunkedBy(::isEven) shouldEqual listOf(listOf(1), listOf(2))
        listOf(1, 2, 2, 4, 3, 1).chunkedBy(::isEven) shouldEqual listOf(
            listOf(1),
            listOf(2, 2, 4),
            listOf(3, 1)
        )
    }
}