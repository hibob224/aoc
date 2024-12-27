package utils

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import java.security.MessageDigest
import kotlin.math.roundToInt

fun Boolean?.orFalse(): Boolean = this ?: false

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

fun <T> Iterable<T>.combinations(length: Int): Sequence<List<T>> =
    sequence {
        val pool = this@combinations as? List<T> ?: toList()
        val n = pool.size
        if (length > n) return@sequence
        val indices = IntArray(length) { it }
        while (true) {
            yield(indices.map { pool[it] })
            var i = length
            do {
                i--
                if (i == -1) return@sequence
            } while (indices[i] == i + n - length)
            indices[i]++
            for (j in i + 1 until length) indices[j] = indices[j - 1] + 1
        }
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

fun List<Int>.product(): Int = reduce(Int::times)
fun List<Long>.product(): Long = reduce(Long::times)

fun JsonArray.transform(): List<Any> = map {
    if (it is JsonArray) {
        it.transform()
    } else {
        it.jsonPrimitive.int
    }
}

fun String.replaceAll(replacements: List<Pair<String, String>>): String = replacements.fold(this) { acc, replacement ->
    acc.replace(replacement.first, replacement.second)
}

fun gcd(x: Long, y: Long): Long = if (y == 0L) x else gcd(y, x % y)
fun List<Long>.gcd(): Long = reduce { x, y -> gcd(x, y) }

fun lcm(x: Long, y: Long): Long = x * (y / gcd(x, y))
fun List<Long>.lcm(): Long = reduce { x, y -> lcm(x, y) }

//region Input
fun Any.getInputFile(example: Boolean = false): File = getInputFile(this::class.java.packageName, example)
private fun getInputFile(packageName: String, example: Boolean = false): File = File("src/main/kotlin/${packageName.replace('.', '/')}/${if (example) "example" else "input"}.txt")

fun File.longs(seperator: String = "\n") = strings(seperator).map(String::toLong)
fun File.strings(seperator: String = "\n") = readText().split(seperator)
fun String.lines() = split("\n")
//endregion

fun <T> List<T>.permutations(): List<List<T>> {
    return if (this.size == 1) listOf(this)
    else this.flatMap { i -> (this - i).permutations().map { listOf(i) + it } }
}

fun String.permutations() = this.toList().permutations().map { it.joinToString("") }

fun <T> Sequence<T>.repeat() = sequence {
    while (true) {
        yieldAll(this@repeat)
    }
}

fun isCi(): Boolean = System.getenv("CI").toBoolean()

/**
 * Find index of [subList] within [mainList]
 */
fun findSubsequenceIndex(mainList: List<Long>, subList: List<Long>): Int {
    if (subList.isEmpty()) return 0 // Empty subsequence is always found at the beginning

    for (i in mainList.indices) {
        if (i + subList.size > mainList.size) break // Avoid out-of-bounds access
        if (mainList.subList(i, i + subList.size) == subList) return i
    }
    return -1
}

fun <T> T.print() = also(::println)

fun <T> List<T>.toPair(): Pair<T, T> {
    check(size == 2)
    return get(0) to get(1)
}
