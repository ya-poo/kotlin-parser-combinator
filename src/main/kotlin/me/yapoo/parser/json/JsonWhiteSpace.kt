package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.any
import me.yapoo.parser.core.char
import me.yapoo.parser.core.many
import me.yapoo.parser.core.map

fun jsonWhiteSpaces(): Parser<Unit> =
    listOf(
        char(' '),
        char('\n'),
        char('\r'),
        char('\t')
    ).any().many().map { }
