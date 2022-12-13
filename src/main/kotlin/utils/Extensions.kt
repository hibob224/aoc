package utils

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import java.security.MessageDigest
import kotlin.math.roundToInt

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0

fun Int.isMultipleOf(x: Int): Boolean = this % x == 0

fun Long.isMultipleOf(x: Long): Boolean = this % x == 0L

val Int.isEven: Boolean
    get() = isMultipleOf(2)

val IntRange.upperRange: IntRange
    get() = (last - ((last - first).toFloat().div(2).toInt()))..last
val IntRange.lowerRange: IntRange
    get() = first..(last - ((last - first).toFloat().div(2).roundToInt()))

val String.md5: String
    get() = MessageDigest.getInstance("MD5")
        .digest(this.toByteArray())
        .joinToString("") { "%02x".format(it) }

fun <T> allPermutations(set: Set<T>): Set<List<T>> {
    if (set.isEmpty()) return emptySet()

    fun <T> _allPermutations(list: List<T>): Set<List<T>> {
        if (list.isEmpty()) return setOf(emptyList())

        val result: MutableSet<List<T>> = mutableSetOf()
        for (i in list.indices) {
            _allPermutations(list - list[i]).forEach { item ->
                result.add(item + list[i])
            }
        }
        return result
    }

    return _allPermutations(set.toList())
}

fun roundUpToMultiple(n: Int, multipleOf: Int): Int {
    return (n + multipleOf - 1) / multipleOf * multipleOf
}

fun String.hexToBinary(): String = toCharArray().joinToString("") {
    when (it) {
        '0' -> "0000"
        '1' -> "0001"
        '2' -> "0010"
        '3' -> "0011"
        '4' -> "0100"
        '5' -> "0101"
        '6' -> "0110"
        '7' -> "0111"
        '8' -> "1000"
        '9' -> "1001"
        'A' -> "1010"
        'B' -> "1011"
        'C' -> "1100"
        'D' -> "1101"
        'E' -> "1110"
        'F' -> "1111"
        else -> throw IllegalArgumentException("Illegal hex char")
    }
}

fun <T> List<T?>.groupOnNulls(): List<List<T>> = buildList {
    var current = mutableListOf<T>()
    this@groupOnNulls.forEach {
        it?.let(current::add) ?: run {
            add(current)
            current = mutableListOf()
        }
    }
    add(current)
}

fun JsonArray.transform(): List<Any> = map {
    if (it is JsonArray) {
        it.transform()
    } else {
        it.jsonPrimitive.int
    }
}
