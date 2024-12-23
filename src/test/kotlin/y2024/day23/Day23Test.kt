package y2024.day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day23Test {

    private val day = Day23()

    @Test
    fun solvePartOne() {
        assertEquals(1227, day.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals("cl,df,ft,ir,iy,ny,qp,rb,sh,sl,sw,wm,wy", day.solvePartTwo())
    }
}
