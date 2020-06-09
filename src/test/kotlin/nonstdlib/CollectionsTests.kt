package nonstdlib

import datsok.shouldEqual
import org.junit.Test

class CollectionsTests {
    @Test fun `join iterable to string`() {
        listOf(1, 2, 3).join("|") shouldEqual "1|2|3"
        listOf(1, 2, 3).joinBy("|") { (it - 1).toString() } shouldEqual "0|1|2"
    }

    @Test fun `tail of list and iterable`() {
        emptyList<Int>().tail() shouldEqual emptyList()
        listOf(1, 2, 3).tail() shouldEqual listOf(2, 3)
    }

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

        listOf(1, 2, 2, 4, 3, 1).iterator().chunkedBy(::isEven).toList() shouldEqual listOf(
            listOf(1),
            listOf(2, 2, 4),
            listOf(3, 1)
        ).iterator().toList()
    }

    @Test fun `sum collection as Long`() {
        listOf(Int.MAX_VALUE, Int.MAX_VALUE).sumByLong { it.toLong() } shouldEqual 4294967294L
        arrayOf(Int.MAX_VALUE, Int.MAX_VALUE).sumByLong { it.toLong() } shouldEqual 4294967294L
    }
}