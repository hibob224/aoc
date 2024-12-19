package utils

//region 2 params
interface Memo2<A, B, R> {
    fun recurse(a: A, b: B): R
}

abstract class Memoized2<A, B, R> {
    private data class Input<A, B>(
        val a: A,
        val b: B
    )

    private val cache = mutableMapOf<Input<A, B>, R>()
    private val memo = object : Memo2<A, B, R> {
        override fun recurse(a: A, b: B): R =
            cache.getOrPut(Input(a, b)) { function(a, b) }
    }

    protected abstract fun Memo2<A, B, R>.function(a: A, b: B): R

    fun execute(a: A, b: B): R = memo.recurse(a, b)
}

fun <A, B, R> (Memo2<A, B, R>.(A, B) -> R).memoize(): (A, B) -> R {
    val memoized = object : Memoized2<A, B, R>() {
        override fun Memo2<A, B, R>.function(a: A, b: B): R = this@memoize(a, b)
    }
    return { a, b ->
        memoized.execute(a, b)
    }
}
//endregion
