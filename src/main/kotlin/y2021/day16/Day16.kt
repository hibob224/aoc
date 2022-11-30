package y2021.day16

import utils.hexToBinary
import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    println("Part one: ${Day16.solvePartOne()}")
    println("Part two: ${Day16.solvePartTwo()}")
}

object Day16 {

    private val directory: String
        get() = this::class.java.`package`.name.replace('.', '/')

    private val packet = Packet(File("src/main/kotlin/$directory/input.txt").readLines().first().hexToBinary())

    fun solvePartOne(): Int = packet.versionSum()

    fun solvePartTwo(): Long = packet.calculate()
}

private class Packet(binary: String) {
    private val version = binary.take(3).toInt(2)
    private val type: Int = binary.substring(3..5).toInt(2)
    private val subpackets = mutableListOf<Packet>()
    // Value stored for 'literal' packets
    private var value = 0L
    // Keeps track of how many chars from binary have been consumed by this packet, and all it's subpackets. This allows
    // this packets parent to know where in the binary to start from
    var consumed = 0

    init {
        if (type == 4) {
            var index = 6
            var binaryValue = ""
            while (true) { // Keep reading binary digits until we reach one marked as 'final'
                val value = binary.substring(index, index + 5)
                binaryValue += value.substring(1, value.length)
                index += 5

                if (value.first() == '0') { // This was the final digit, break out the loop now
                    break
                }
            }
            value = binaryValue.toLong(2) // Convert stored binary into decimal
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
            // Keep track of how many bits have been consumed, we'll stop once we reach subpacketBitLength
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

    fun versionSum(): Int = version + subpackets.sumOf(Packet::versionSum)

    fun calculate(): Long = when (type) {
        0 -> subpackets.map(Packet::calculate).sum() // Sum
        1 -> subpackets.fold(1) { acc, packet -> acc * packet.calculate() } // Product
        2 -> subpackets.minOf(Packet::calculate) // Min
        3 -> subpackets.maxOf(Packet::calculate) // Max
        4 -> value // Literal
        5 -> if (subpackets[0].calculate() > subpackets[1].calculate()) 1 else 0 // Greater than
        6 -> if (subpackets[0].calculate() < subpackets[1].calculate()) 1 else 0 // Less than
        7 -> if (subpackets[0].calculate() == subpackets[1].calculate()) 1 else 0 // Equals
        else -> throw IllegalArgumentException("Unknown type - $type")
    }

    override fun toString(): String = "$version - $type - $value - $consumed"
}

