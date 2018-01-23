
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import kotlincommon.chunkedBy
import kotlincommon.permutations
import kotlincommon.skip
import org.junit.Test

class UtilTests {

    @Test fun `iterator with skipped elements`() {
        listOf(1, 2, 3).skip(0).toList() shouldEqual listOf(1, 2, 3)
        listOf(1, 2, 3).skip(1).toList() shouldEqual listOf(2, 3)
        listOf(1, 2, 3).skip(2).toList() shouldEqual listOf(3)
        listOf(1, 2, 3).skip(3).toList() shouldEqual listOf()
    }

    @Test fun `permutation of elements`() {
        listOf<Int>().permutations() shouldEqual listOf(listOf())
        listOf(1).permutations() shouldEqual listOf(listOf(1))
        listOf(1, 2).permutations() shouldEqual listOf(listOf(1, 2), listOf(2, 1))
        listOf(2, 2).permutations() shouldEqual listOf(listOf(2, 2))
        listOf(1, 2, 3).permutations() shouldEqual listOf(
            listOf(1, 2, 3),
            listOf(1, 3, 2),
            listOf(2, 1, 3),
            listOf(2, 3, 1),
            listOf(3, 1, 2),
            listOf(3, 2, 1)
        )
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

infix fun <T> T.shouldEqual(that: T) {
    assertThat(this, equalTo(that))
}

infix fun <T> T.shouldNotEqual(that: T) {
    assertThat(this, !equalTo(that))
}