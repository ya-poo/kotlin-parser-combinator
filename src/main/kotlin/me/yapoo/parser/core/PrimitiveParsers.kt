package me.yapoo.parser.core

object PrimitiveParsers {

    fun anyChar(): Parser<Char> = { input ->
        if (input.isNotEmpty()) {
            ParseSuccess(
                value = input.first(),
                rest = input.drop(1)
            )
        } else {
            ParseFailure
        }
    }

    fun <A> succeed(a: A): Parser<A> = { input ->
        ParseSuccess(value = a, rest = input)
    }

    fun <A> failed(): Parser<A> = { ParseFailure }

    fun eof(): Parser<Unit> = { input ->
        if (input.isEmpty()) {
            ParseSuccess(Unit, input)
        } else {
            ParseFailure
        }
    }
}
