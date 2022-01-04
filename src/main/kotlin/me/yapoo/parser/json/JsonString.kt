package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.PrimitiveParsers.anyChar
import me.yapoo.parser.core.any
import me.yapoo.parser.core.char
import me.yapoo.parser.core.defer
import me.yapoo.parser.core.digit
import me.yapoo.parser.core.exclude
import me.yapoo.parser.core.listOfN
import me.yapoo.parser.core.many
import me.yapoo.parser.core.map
import me.yapoo.parser.core.or
import me.yapoo.parser.core.product
import me.yapoo.parser.core.string
import me.yapoo.parser.core.zip

fun jsonString(): Parser<JsonString> =
    jsonStringChar().many().map { it.joinToString(separator = "") }
        .quoted()
        .map(::JsonString)

fun Parser<String>.quoted(): Parser<String> =
    (char('"') product this.defer() product char('"').defer()).map { it.first.second }

fun jsonStringChar(): Parser<Char> =
    escapeCharacters() or (anyChar()
            exclude char('"').defer()
            exclude char('\\').defer()
            exclude controlCharacter().defer()).defer()

fun escapeCharacters() = listOf(
    quotationMark(),
    reverseSolidus(),
    solidus(),
    backspace(),
    formFeed(),
    linefeed(),
    carriageReturn(),
    horizontalTab(),
    unicode()
).any()

fun quotationMark(): Parser<Char> = string("""\"""").map { '"' }

fun reverseSolidus(): Parser<Char> = string("""\\""").map { '\\' }

fun solidus(): Parser<Char> = string("""\/""").map { '/' }

fun backspace(): Parser<Char> = string("""\b""").map { '\b' }

fun formFeed(): Parser<Char> = string("""\f""").map { '\u000C' }

fun linefeed(): Parser<Char> = string("""\n""").map { '\n' }

fun carriageReturn(): Parser<Char> = string("""\r""").map { '\r' }

fun horizontalTab(): Parser<Char> = string("""\t""").map { '\t' }

fun unicode(): Parser<Char> = string("""\u""").zip(hexDigits().defer()) { _, code ->
    code.joinToString(separator = "").toInt(16).toChar()
}

fun controlCharacter(): Parser<Char> = char { c ->
    c.code <= 0x1f
}

private fun hexDigits(): Parser<List<Char>> =
    (digit() or hexAlphabets().defer()).listOfN(4)

private fun hexAlphabets(): Parser<Char> =
    listOf('A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f').map { char(it) }.any()
