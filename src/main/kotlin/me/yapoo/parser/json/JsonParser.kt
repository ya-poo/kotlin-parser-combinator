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
import me.yapoo.parser.core.negativeBigInteger
import me.yapoo.parser.core.optional
import me.yapoo.parser.core.or
import me.yapoo.parser.core.unsignedBigInteger
import me.yapoo.parser.core.zip
import java.math.BigDecimal
import java.math.BigInteger

// see https://www.json.org/json-ja.html

sealed class Json

object JsonNull : Json()
data class JsonNumber(val get: BigDecimal) : Json() {
    constructor(double: Double) : this(BigDecimal.valueOf(double))
}

data class JsonInteger(val get: BigInteger) : Json() {
    constructor(integer: Int) : this(BigInteger.valueOf(integer.toLong()))
}

data class JsonString(val get: String) : Json()
data class JsonBoolean(val get: Boolean) : Json()
data class JsonArray(val get: List<Json>) : Json()
data class JsonObject(val get: Map<String, Json>) : Json()


// Integer
fun jsonInteger(): Parser<JsonInteger> =
    (unsignedBigInteger() or { negativeBigInteger() }).map(::JsonInteger)

// Number
fun jsonNumber(): Parser<JsonNumber> =
    jsonInteger().zip(
        jsonNumberFraction().optional().defer()
    ) { integer, fraction ->
        integer.get.toDouble().toBigDecimal() + fraction.getOrElse { BigDecimal.ZERO }
    }.zip(
        jsonNumberExponent().optional().defer()
    ) { number, exponent ->
        number * exponent.getOrElse { BigDecimal.ONE }
    }.map(::JsonNumber)

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
