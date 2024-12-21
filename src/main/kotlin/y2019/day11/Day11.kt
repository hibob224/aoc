package y2019.day11

import IntCodeComputer
import utils.Point
import utils.getInputFile
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.*
import javax.imageio.ImageIO

fun main() {
    println("Part one: ${Day11.solvePartOne()}")
    println("Part two: ${Day11.solvePartTwo()}")
}

object Day11 {

    private fun parseInput(): List<Long> = getInputFile()
        .readLines()[0]
        .split(",")
        .map { it.toLong() }

    fun solvePartOne(): String {
        val computerOutput = LinkedBlockingQueue<Long>()

        val computer = IntCodeComputer(parseInput().toLongArray()).also {
            it.outputArray = computerOutput
        }

        Executors.newCachedThreadPool().also {
            it.submit(computer)
            return it.submit(Robot(computerOutput, computer)).get().toString()
        }
    }

    fun solvePartTwo(): String {
        val computerOutput = LinkedBlockingQueue<Long>()
        var testOutput = ""

        val computer = IntCodeComputer(parseInput().toLongArray()).also {
            it.outputArray = computerOutput
        }
        val robot = Robot(computerOutput, computer).also {
            it.points[Point(0, 0)] = 1L
        }

        Executors.newCachedThreadPool().also {
            it.submit(computer)
            it.submit(robot).get()

            val minX = robot.points.keys.minBy { it.x }.x
            val minY = robot.points.keys.minBy { it.y }.y
            val maxX = robot.points.keys.maxBy { it.x }.x
            val maxY = robot.points.keys.maxBy { it.y }.y

            val width = maxX - minX
            val height = maxY - minY

            val image = BufferedImage(width.inc(), height.inc(), BufferedImage.TYPE_INT_RGB)
            robot.points.forEach { (t, u) ->
                image.setRGB(t.x - minX, t.y - minY, if (u == 1L) Color.WHITE.rgb else Color.BLACK.rgb)
                testOutput += if (u == 1L) "#" else "."
            }
            ImageIO.write(image, "png", File("src/main/kotlin/y2019/day11/Day11.png"))
        }

        return testOutput
    }
}

class Robot(
    val input: BlockingQueue<Long>,
    private val computer: IntCodeComputer): Callable<Int> {
    val points = mutableMapOf<Point, Long>()
    private var x = 0
    private var y = 0
    private var direction = Direction.UP

    override fun call(): Int {
        while (true) {
            computer.addInput(points.getOrDefault(Point(x, y), 0L)) // Give current colour to computer, 0=Black, 1=White
            // Poll for input because I'm too lazy to make a real way of breaking out this loop
            points[Point(x, y)] = input.poll(10, TimeUnit.MILLISECONDS) ?: break // Apply colour
            direction = direction.turn(input.poll(10, TimeUnit.MILLISECONDS) ?: break) // Turn
            val coord = direction.move(x, y)
            x = coord.first
            y = coord.second
        }

        return points.size
    }
}

enum class Direction {
    UP, RIGHT, DOWN, LEFT;

    fun turn(direction: Long): Direction {
        return when (this) {
            UP -> if (direction == 0L) LEFT else RIGHT
            RIGHT -> if (direction == 0L) UP else DOWN
            DOWN -> if (direction == 0L) RIGHT else LEFT
            LEFT -> if (direction == 0L) DOWN else UP
        }
    }

    fun move(x: Int, y: Int): Pair<Int, Int> {
        return when (this) {
            UP -> x to y.dec()
            RIGHT -> x.inc() to y
            DOWN -> x to y.inc()
            LEFT -> x.dec() to y
        }
    }
}
