package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.defer
import me.yapoo.parser.core.map
import me.yapoo.parser.core.minusZeroBigInteger
import me.yapoo.parser.core.negativeBigInteger
import me.yapoo.parser.core.or
import me.yapoo.parser.core.unsignedBigInteger
import java.math.BigInteger

@Deprecated("use JsonNumber", ReplaceWith("me.yapoo.parser.json.jsonNumber"))
fun jsonInteger(): Parser<JsonInteger> =
    (unsignedBigInteger() or negativeBigInteger().defer() or minusZeroBigInteger().defer()).map(::JsonInteger)

@Deprecated("use JsonNumber", ReplaceWith("me.yapoo.parser.json.JsonNumber"))
data class JsonInteger(val get: BigInteger) : Json() {
    constructor(integer: Int) : this(BigInteger.valueOf(integer.toLong()))
}
