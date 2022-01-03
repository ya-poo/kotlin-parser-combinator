package me.yapoo.parser.core

import me.yapoo.parser.cons

fun unsignedInteger(): Parser<Int> = zeroInteger() or { nonZeroInteger() }

fun signedInteger(): Parser<Int> =
    char('+').zip({ unsignedInteger() }) { _, integer -> integer } or
            { char('-').zip({ unsignedInteger() }) { _, integer -> -1 * integer } }

fun integer(): Parser<Int> = unsignedInteger() or { signedInteger() }

private fun nonZeroInteger(): Parser<Int> = ((digit() exclude { char('0') }) product { digit().many() })
    .map { (char, chars) ->
        (char cons chars).joinToString(separator = "").toInt()
    }

private fun zeroInteger(): Parser<Int> = char('0').map { 0 }
