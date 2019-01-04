import kotlincommon.*
import kotlincommon.test.shouldEqual
import org.junit.Test
import java.math.BigInteger

class UtilTests {

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

    @Test fun `factorial calculations for Int and Long`() {
        val input = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        val factorials = listOf(1, 1, 2, 6, 24, 120, 720, 5040, 40320)

        input.map { it.intFactorial() } shouldEqual factorials
        input.map { it.longFactorial() } shouldEqual factorials.map(Int::toLong)
        input.map { it.factorial() } shouldEqual factorials.map(Int::toBigInteger)
    }

    @Test fun `factorial calculations for BigInteger`() {
        100.toBigInteger().factorial() shouldEqual BigInteger("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000")
    }

    @Test fun `permutations of elements`() {
        listOf<Int>().permutations() shouldEqual listOf(listOf())
        listOf(1).permutations() shouldEqual listOf(listOf(1))
        listOf(1, 2).permutations() shouldEqual listOf(listOf(1, 2), listOf(2, 1))
        listOf(2, 2).permutations() shouldEqual listOf(listOf(2, 2), listOf(2, 2))
        listOf(1, 2, 3).permutations() shouldEqual listOf(
            listOf(1, 2, 3),
            listOf(1, 3, 2),
            listOf(2, 1, 3),
            listOf(2, 3, 1),
            listOf(3, 1, 2),
            listOf(3, 2, 1)
        )
    }

    @Test fun `sequence of permutations`() {
        emptyList<Int>().permutationsSequence().toList() shouldEqual listOf(emptyList())
        listOf(1).permutationsSequence().toList() shouldEqual listOf(listOf(1))
        (0..20).toList().permutationsSequence().take(2).toList() shouldEqual listOf(
            listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20),
            listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 19)
        )
    }
}
