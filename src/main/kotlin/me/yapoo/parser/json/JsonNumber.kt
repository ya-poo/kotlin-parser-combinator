package me.yapoo.parser.json

import arrow.core.Some
import arrow.core.getOrElse
import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.char
import me.yapoo.parser.core.defer
import me.yapoo.parser.core.digit
import me.yapoo.parser.core.many
import me.yapoo.parser.core.many1
import me.yapoo.parser.core.map
import me.yapoo.parser.core.minusZeroBigInteger
import me.yapoo.parser.core.negativeBigInteger
import me.yapoo.parser.core.optional
import me.yapoo.parser.core.or
import me.yapoo.parser.core.unsignedBigInteger
import me.yapoo.parser.core.zip
import java.math.BigDecimal

fun jsonNumber(): Parser<JsonNumber> = run {
    fun Parser<BigDecimal>.applyExponent(): Parser<JsonNumber> =
        zip(jsonNumberExponent().optional().defer()) { number, exponent ->
            if (number == BigDecimal.ZERO) number
            else number * exponent.getOrElse { BigDecimal.ONE }
        }.map(::JsonNumber)

    fun jsonNegativeNumber(): Parser<JsonNumber> =
        (negativeBigInteger() or minusZeroBigInteger().defer()).zip(
            jsonNumberFraction().optional().defer()
        ) { integer, fraction ->
            integer.toBigDecimal() - fraction.getOrElse { BigDecimal.ZERO }
        }.applyExponent()

    fun jsonPositiveNumber(): Parser<JsonNumber> =
        unsignedBigInteger().zip(
            jsonNumberFraction().optional().defer()
        ) { integer, fraction ->
            integer.toBigDecimal() + fraction.getOrElse { BigDecimal.ZERO }
        }.applyExponent()

    jsonPositiveNumber() or jsonNegativeNumber().defer()
}


fun jsonNumberFraction(): Parser<BigDecimal> =
    char('.').zip({ digit().many() }) { _, digits ->
        if (digits.all { it == '0' }) BigDecimal.ZERO
        else {
            "0.${digits.joinToString(separator = "")}".toBigDecimal()
        }
    }

fun jsonNumberExponent(): Parser<BigDecimal> =
    (char('E') or char('e').defer()).zip(
        (char('-') or char('+').defer()).optional().zip(digit().many1().defer()) { sign, digits ->
            val value = String(digits.toCharArray()).toInt()
            if (sign is Some && sign.value == '-')
                -1 * value
            else
                value
        }.defer()
    ) { _, exponent ->
        when {
            exponent == 0 -> BigDecimal.ONE
            exponent < 0 -> {
                BigDecimal.valueOf(0.1).pow(-1 * exponent)
            }
            else -> {
                BigDecimal.TEN.pow(exponent)
            }
        }
    }
