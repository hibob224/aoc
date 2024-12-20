package y2024.day20

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day20Test {
    @Test
    fun solvePartOne() {
        runBlocking {
            assertEquals(1321, Day20.solvePartOne())
        }
    }

    @Test
    fun solvePartTwo() {
        runBlocking {
            assertEquals(971737, Day20.solvePartTwo())
        }
    }
}
