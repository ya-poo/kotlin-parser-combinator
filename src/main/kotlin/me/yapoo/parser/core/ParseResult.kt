package me.yapoo.parser.core

sealed class ParseResult<out A>

data class ParseSuccess<out A>(
    val value: A,
    val rest: String,
) : ParseResult<A>()

object ParseFailure : ParseResult<Nothing>()
