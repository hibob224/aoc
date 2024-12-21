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

//region 3 params
interface Memo3<A, B, C, R> {
    fun recurse(a: A, b: B, c: C): R
}

abstract class Memoized3<A, B, C, R> {
    private data class Input<A, B, C>(
        val a: A,
        val b: B,
        val c: C,
    )

    private val cache = mutableMapOf<Input<A, B, C>, R>()
    private val memo = object : Memo3<A, B, C, R> {
        override fun recurse(a: A, b: B, c: C): R =
            cache.getOrPut(Input(a, b, c)) { function(a, b, c) }
    }

    protected abstract fun Memo3<A, B, C, R>.function(a: A, b: B, c: C): R

    fun execute(a: A, b: B, c: C): R = memo.recurse(a, b, c)
}

fun <A, B, C, R> (Memo3<A, B, C, R>.(A, B, C) -> R).memoize(): (A, B, C) -> R {
    val memoized = object : Memoized3<A, B, C, R>() {
        override fun Memo3<A, B, C, R>.function(a: A, b: B, c: C): R = this@memoize(a, b, c)
    }
    return { a, b, c ->
        memoized.execute(a, b, c)
    }
}
//endregion
