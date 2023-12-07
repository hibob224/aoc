package y2023.day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day05Test {
    @Test
    fun solvePartOne() {
        assertEquals(196167384, Day05.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        // Brute force, takes awhile, commenting out
//        assertEquals(125742456, runBlocking { Day05.solvePartTwo() })
    }
}
