package me.yapoo.parser.json

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.boolean
import me.yapoo.parser.core.map

fun jsonBoolean(): Parser<JsonBoolean> = boolean().map(::JsonBoolean)
