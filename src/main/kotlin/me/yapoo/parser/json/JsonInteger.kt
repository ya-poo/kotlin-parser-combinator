package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.defer
import me.yapoo.parser.core.map
import me.yapoo.parser.core.minusZeroBigInteger
import me.yapoo.parser.core.negativeBigInteger
import me.yapoo.parser.core.or
import me.yapoo.parser.core.unsignedBigInteger

fun jsonInteger(): Parser<JsonInteger> =
    (unsignedBigInteger() or negativeBigInteger().defer() or minusZeroBigInteger().defer()).map(::JsonInteger)
