package me.yapoo.parser.json

import java.math.BigDecimal

// see https://www.json.org/json-ja.html

sealed class Json

object JsonNull : Json()
data class JsonNumber(val get: BigDecimal) : Json() {
    constructor(double: Double) : this(BigDecimal.valueOf(double))
    constructor(long: Long) : this(BigDecimal.valueOf(long))
}

data class JsonString(val get: String) : Json()
data class JsonBoolean(val get: Boolean) : Json()
data class JsonArray(val get: List<Json>) : Json() {
    constructor(vararg values: Json) : this(values.toList())
}

data class JsonObject(val get: Map<String, Json>) : Json() {
    constructor(vararg values: Pair<String, Json>) : this(values.toMap())
}
