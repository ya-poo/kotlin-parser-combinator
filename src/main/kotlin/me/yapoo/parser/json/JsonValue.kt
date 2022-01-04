package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.defer
import me.yapoo.parser.core.map
import me.yapoo.parser.core.or
import me.yapoo.parser.core.product

fun jsonValue(): Parser<Json> =
    (jsonWhiteSpaces()
            product jsonActualValue().defer()
            product jsonWhiteSpaces().defer()).map { it.first.second }

private fun jsonActualValue(): Parser<Json> =
    (jsonString()
            or jsonNumber().defer()
            or jsonBoolean().defer()
            or jsonNull().defer()
            or jsonArray().defer()
            or jsonObject().defer())
