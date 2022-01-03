package me.yapoo.parser.core

import me.yapoo.parser.core.PrimitiveParsers.succeed
import me.yapoo.parser.cons

typealias Parser<A> = (String) -> ParseResult<A>

fun <A, B> Parser<A>.flatMap(
    f: (A) -> Parser<B>
): Parser<B> = { input ->
    when (val result = this(input)) {
        is ParseSuccess -> {
            f(result.value)(result.rest)
        }
        is ParseFailure -> result
    }
}

infix fun <A> Parser<A>.or(
    other: () -> Parser<A>
): Parser<A> = { input ->
    when (val result1 = this(input)) {
        is ParseSuccess -> result1
        is ParseFailure -> {
            other()(input)
        }
    }
}

infix fun <A, B> Parser<A>.exclude(
    other: () -> Parser<B>
): Parser<A> = { input ->
    when (val result1 = this(input)) {
        is ParseSuccess -> {
            when (other()(input)) {
                is ParseFailure -> result1
                is ParseSuccess -> ParseFailure
            }
        }
        is ParseFailure -> result1
    }
}

fun <A> Parser<A>.many1(): Parser<List<A>> =
    zip({ many() }) { a, la ->
        a cons la
    }

fun <A> Parser<A>.many(): Parser<List<A>> =
    many1() or { succeed(emptyList()) }

fun <A, B> Parser<A>.map(
    f: (A) -> B
): Parser<B> = flatMap { a ->
    succeed(f(a))
}

fun <A> Parser<A>.listOfN(n: Int): Parser<List<A>> =
    if (n > 0) {
        zip({ listOfN(n - 1) }) { a, la ->
            a cons la
        }
    } else {
        succeed(emptyList())
    }

infix fun <A, B> Parser<A>.product(
    other: () -> Parser<B>
): Parser<Pair<A, B>> =
    flatMap { a ->
        other().map { b ->
            a to b
        }
    }

fun <A, B, C> Parser<A>.zip(
    pb: () -> Parser<B>,
    f: (A, B) -> C
): Parser<C> =
    flatMap { a ->
        pb().map { b ->
            f(a, b)
        }
    }
