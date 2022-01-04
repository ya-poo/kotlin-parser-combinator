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
import me.yapoo.parser.core.negativeInteger
import me.yapoo.parser.core.optional
import me.yapoo.parser.core.or
import me.yapoo.parser.core.unsignedInteger
import me.yapoo.parser.core.zip
import kotlin.math.pow

// see https://www.json.org/json-ja.html

sealed class Json

object JsonNull : Json()
data class JsonNumber(val get: Double) : Json()
data class JsonInteger(val get: Int) : Json()
data class JsonString(val get: String) : Json()
data class JsonBoolean(val get: Boolean) : Json()
data class JsonArray(val get: List<Json>) : Json()
data class JsonObject(val get: Map<String, Json>) : Json()


// Integer
fun jsonInteger(): Parser<JsonInteger> =
    (unsignedInteger() or { negativeInteger() }).map(::JsonInteger)

// Number
fun jsonNumber(): Parser<JsonNumber> =
    jsonInteger().zip(
        jsonNumberFraction().optional().defer()
    ) { integer, fraction ->
        integer.get.toDouble() + fraction.getOrElse { 0.0 }
    }.zip(
        jsonNumberExponent().optional().defer()
    ) { number, exponent ->
        number * exponent.getOrElse { 1.0 }
    }.map(::JsonNumber)


fun jsonNumberFraction(): Parser<Double> =
    char('.').zip({ digit().many() }) { _, digits ->
        if (digits.all { it == '0' }) 0.0
        else {
            "0.${digits.joinToString(separator = "")}".toDouble()
        }
    }

fun jsonNumberExponent(): Parser<Double> =
    (char('E') or char('e').defer()).zip(
        (char('-') or char('+').defer()).optional().zip(digit().many1().defer()) { sign, digits ->
            val value = String(digits.toCharArray()).toInt()
            if (sign is Some && sign.value == '-')
                -1 * value
            else
                value
        }.defer()
    ) { _, exponent ->
        10.0.pow(exponent)
    }