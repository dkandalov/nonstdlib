package nonstdlib

import datsok.shouldEqual
import org.junit.Test
import java.math.BigInteger
import kotlin.random.Random

class AlgorithmsTests {
    @Test fun `power functions`() {
        3.pow(4) shouldEqual 81
        3L.pow(4L) shouldEqual 81L
    }

    @Test fun `factorial for Int and Long`() {
        val input = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        val factorials = listOf(1, 1, 2, 6, 24, 120, 720, 5040, 40320)

        input.map { it.intFactorial() } shouldEqual factorials
        input.map { it.longFactorial() } shouldEqual factorials.map(Int::toLong)
        input.map { it.factorial() } shouldEqual factorials.map(Int::toBigInteger)
    }

    @Test fun `factorial for BigInteger`() {
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

    @Test fun `random list of Ints`() {
        Random.listOfInts(size = 3, valuesRange = 0..10).let { list ->
            list.size shouldEqual 3
            list.all { it in 0..10 }
        }
        Random.listOfInts(sizeRange = 0..10, valuesRange = 0..10).let { list ->
            (list.size in 0..10) shouldEqual true
            list.all { it in 0..10 }
        }
    }

    @Test fun `swap collection items`() {
        arrayOf(1, 2, 3).let {
            it.swap(0, 2)
            it shouldEqual arrayOf(3, 2, 1)
        }
        mutableListOf(1, 2, 3).let {
            it.swap(0, 2)
            it shouldEqual mutableListOf(3, 2, 1)
        }
    }
}
