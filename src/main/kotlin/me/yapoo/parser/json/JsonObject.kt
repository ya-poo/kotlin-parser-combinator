package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.char
import me.yapoo.parser.core.defer
import me.yapoo.parser.core.many
import me.yapoo.parser.core.map
import me.yapoo.parser.core.or
import me.yapoo.parser.core.product

fun jsonObject(): Parser<JsonObject> =
    jsonEmptyObject() or { jsonNonEmptyObject() }

fun jsonEmptyObject(): Parser<JsonObject> =
    (char('{') product jsonWhiteSpaces().defer() product char('}').defer())
        .map { JsonObject(emptyMap()) }

fun jsonNonEmptyObject(): Parser<JsonObject> =
    (char('{')
            product jsonObjectRecord().many().defer()
            product jsonObjectLastRecord().defer()
            product char('}').defer()).map {
        val records = it.first.first.second + it.first.second
        JsonObject(records.toMap())
    }


private fun jsonObjectRecord(): Parser<Pair<String, Json>> =
    (jsonObjectRecordCommon()
            product char(',').defer()).map {
        val key = it.first.first.first.first.second
        val value = it.first.second
        key.get to value
    }

private fun jsonObjectLastRecord(): Parser<Pair<String, Json>> =
    jsonObjectRecordCommon().map {
        val key = it.first.first.first.second
        val value = it.second
        key.get to value
    }

private fun jsonObjectRecordCommon() =
    (jsonWhiteSpaces()
            product jsonString().defer()
            product jsonWhiteSpaces().defer()
            product char(':').defer()
            product jsonValue().defer())
