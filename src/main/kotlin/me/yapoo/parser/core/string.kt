package me.yapoo.parser.core

import me.yapoo.parser.core.PrimitiveParsers.succeed

fun string(s: String): Parser<String> =
    if (s.isEmpty()) succeed(s)
    else s.toCharArray().fold(
        succeed("")
    ) { acc, c ->
        acc.zip({ char(c) }) { str, char ->
            str + char
        }
    }
