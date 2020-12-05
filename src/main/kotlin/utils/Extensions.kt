package utils

import java.security.MessageDigest
import kotlin.math.roundToInt

fun Int?.orZero(): Int = this ?: 0

fun Int.isMultipleOf(x: Int): Boolean = this % x == 0

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