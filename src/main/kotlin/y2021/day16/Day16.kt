package y2021.day16

import utils.hexToBinary
import java.io.File

fun main() {
    println("Part one: ${Day16.solvePartOne()}")
    println("Part two: ${Day16.solvePartTwo()}")
}

object Day16 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val input: String = File("src/main/kotlin/$directory/input.txt").readLines().first()

    fun solvePartOne(): Int = Packet(input.hexToBinary()).versionSum()

    fun solvePartTwo(): Int {
        return -1
    }
}

private class Packet(binary: String) {
    private val version = binary.take(3).toInt(2)
    private val type: Int = binary.substring(3..5).toInt(2)
    private val subpackets = mutableListOf<Packet>()
    private var value = 0L
    var consumed = 0

    init {
        if (type == 4) {
            var index = 6
            var binaryValue = ""
            while (true) {
                val value = binary.substring(index, index + 5)
                binaryValue += value.substring(1, value.length)
                index += 5

                if (value.first() == '0') {
                    break
                }
            }
            value = binaryValue.toLong(2)
            consumed = index
        } else if (binary[6] == '1') {
            var index = 7
            val numberOfPackets = binary.substring(index, index + 11).toInt(2)
            index += 11
            repeat(numberOfPackets) {
                val packet = Packet(binary.substring(index))
                subpackets += packet
                index += packet.consumed
            }
            consumed = index
        } else {
            var index = 7
            val subpacketBitLength = binary.substring(index, index + 15).toInt(2)
            index += 15
            var subConsume = 0
            while (subConsume < subpacketBitLength) {
                val packet = Packet(binary.substring(index))
                subpackets += packet
                subConsume += packet.consumed
                index += packet.consumed
            }
            consumed = index
        }
    }

    fun versionSum(): Int = version + subpackets.sumBy(Packet::versionSum)

    override fun toString(): String = "$version - $type - $value - $consumed"
}

