package me.yapoo.parser.core

import me.yapoo.parser.cons
import me.yapoo.parser.core.PrimitiveParsers.anyChar
import me.yapoo.parser.core.PrimitiveParsers.failed
import me.yapoo.parser.core.PrimitiveParsers.succeed
import me.yapoo.parser.head
import me.yapoo.parser.tail

object Parsers {
    fun char(p: (Char) -> Boolean): Parser<Char> =
        anyChar().flatMap {
            if (p(it)) succeed(it) else failed()
        }

    fun char(c: Char): Parser<Char> =
        char { it == c }

    fun string(s: String): Parser<String> =
        if (s.isEmpty()) succeed(s)
        else s.toCharArray().fold(
            succeed("")
        ) { acc, c ->
            acc.zip({ char(c) }) { str, char ->
                str + char
            }
        }

    fun boolean(): Parser<Boolean> =
        string("true").map { true } or { string("false").map { false } }

    fun digit(): Parser<Char> =
        (0..9).map { it.digitToChar() }.map { char(it) }.any()

    fun lowerAlphabet(): Parser<Char> = ('a'..'z').map { char(it) }.any()

    fun upperAlphabet(): Parser<Char> = ('A'..'Z').map { char(it) }.any()

    fun alphabet(): Parser<Char> = lowerAlphabet() or { upperAlphabet() }

    fun nonZeroInteger(): Parser<Int> = ((digit() exclude { char('0') }) product { digit().many() })
        .map { (char, chars) ->
            (char cons chars).joinToString(separator = "").toInt()
        }

    fun zeroInteger(): Parser<Int> = char('0').map { 0 }

    fun unsignedInteger(): Parser<Int> = zeroInteger() or { nonZeroInteger() }

    fun signedInteger(): Parser<Int> =
        char('+').zip({ unsignedInteger() }) { _, integer -> integer } or
                { char('-').zip({ unsignedInteger() }) { _, integer -> -1 * integer } }

    fun integer(): Parser<Int> = unsignedInteger() or { signedInteger() }

    private fun <A> List<Parser<A>>.any(): Parser<A> = { input ->
        if (this.isEmpty()) ParseFailure
        else {
            val parser = tail.fold(head) { acc, p ->
                acc or { p }
            }
            parser(input)
        }
    }
}
