package me.yapoo.parser.core

import me.yapoo.parser.core.PrimitiveParsers.anyChar
import me.yapoo.parser.core.PrimitiveParsers.failed
import me.yapoo.parser.core.PrimitiveParsers.succeed

fun char(p: (Char) -> Boolean): Parser<Char> =
    anyChar().flatMap {
        if (p(it)) succeed(it) else failed()
    }

fun char(c: Char): Parser<Char> =
    char { it == c }

fun digit(): Parser<Char> =
    (0..9).map { it.digitToChar() }.map { char(it) }.any()

fun lowerAlphabet(): Parser<Char> = ('a'..'z').map { char(it) }.any()

fun upperAlphabet(): Parser<Char> = ('A'..'Z').map { char(it) }.any()

fun alphabet(): Parser<Char> = lowerAlphabet() or { upperAlphabet() }
