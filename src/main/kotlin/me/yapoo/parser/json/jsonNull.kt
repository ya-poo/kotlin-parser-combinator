package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.map
import me.yapoo.parser.core.string

fun jsonNull(): Parser<JsonNull> =
    string("null").map { JsonNull }
