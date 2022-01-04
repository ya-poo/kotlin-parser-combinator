package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.char
import me.yapoo.parser.core.defer
import me.yapoo.parser.core.many
import me.yapoo.parser.core.map
import me.yapoo.parser.core.or
import me.yapoo.parser.core.product

fun jsonArray(): Parser<JsonArray> =
    jsonEmptyArray() or { jsonNonEmptyArray() }

fun jsonEmptyArray(): Parser<JsonArray> =
    (char('[')
            product jsonWhiteSpaces().defer()
            product char(']').defer()).map { JsonArray(emptyList()) }

fun jsonNonEmptyArray(): Parser<JsonArray> =
    (char('[')
            product jsonArrayRecord().many().defer()
            product jsonArrayLastRecord().defer()
            product char(']').defer()).map {
        JsonArray(it.first.first.second + it.first.second)
    }

private fun jsonArrayRecord(): Parser<Json> =
    (jsonValue() product char(',').defer()).map { it.first }

private fun jsonArrayLastRecord(): Parser<Json> = jsonValue()
