package y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import y2019.day11.Day11

internal class Day11Test {

    @Test
    fun solvePartOne() {
        assertEquals("2478", Day11.solvePartOne())
    }

    @Test
    fun solvePartTwo() {
        assertEquals(".##....##..#.#..#.#..#..##.##..###..#.#..##....##..#.#..#.#...#.##.#....##..###.............#.#....##.##.##...##...##..##....##..#.#..###....#.#...........##..###..###..##....##...#.##.#...###..##...##...#.#..#.#..#.#...#.##.##..##....##..##.##..#..", Day11.solvePartTwo())
    }
}
