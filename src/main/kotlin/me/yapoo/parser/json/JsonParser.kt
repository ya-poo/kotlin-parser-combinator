package me.yapoo.parser.json

import arrow.core.None
import arrow.core.Some
import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.any
import me.yapoo.parser.core.char
import me.yapoo.parser.core.defer
import me.yapoo.parser.core.digit
import me.yapoo.parser.core.many
import me.yapoo.parser.core.map
import me.yapoo.parser.core.negativeBigInteger
import me.yapoo.parser.core.optional
import me.yapoo.parser.core.or
import me.yapoo.parser.core.string
import me.yapoo.parser.core.unsignedBigInteger
import me.yapoo.parser.core.unsignedInteger
import me.yapoo.parser.core.zip
import java.math.BigDecimal
import java.math.BigInteger

// see https://www.json.org/json-ja.html

sealed class Json

object JsonNull : Json()
data class JsonNumber(val get: BigDecimal) : Json()
data class JsonInteger(val get: BigInteger) : Json()
data class JsonString(val get: String) : Json()
data class JsonBoolean(val get: Boolean) : Json()
data class JsonArray(val get: List<Json>) : Json()
data class JsonObject(val get: Map<String, Json>) : Json()


// Integer
fun jsonInteger(): Parser<JsonInteger> =
    (unsignedBigInteger() or { negativeBigInteger() }).map(::JsonInteger)
