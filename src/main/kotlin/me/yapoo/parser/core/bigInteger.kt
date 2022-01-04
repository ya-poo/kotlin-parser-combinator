package me.yapoo.parser.core

import me.yapoo.parser.cons
import java.math.BigInteger

fun unsignedBigInteger(): Parser<BigInteger> = zeroBigInteger() or nonZeroBigInteger().defer()

fun negativeBigInteger(): Parser<BigInteger> =
    char('-').zip(nonZeroBigInteger().defer()) { _, integer -> integer.negate() }

fun minusZeroBigInteger(): Parser<BigInteger> =
    string("-0").map { BigInteger.ZERO }

private fun nonZeroBigInteger(): Parser<BigInteger> = ((digit() exclude { char('0') }) product { digit().many() })
    .map { (char, chars) ->
        (char cons chars).joinToString(separator = "").toBigInteger()
    }

private fun zeroBigInteger(): Parser<BigInteger> = char('0').map { BigInteger.ZERO }
