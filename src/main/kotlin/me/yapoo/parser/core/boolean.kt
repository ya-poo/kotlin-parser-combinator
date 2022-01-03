package me.yapoo.parser.core

fun boolean(): Parser<Boolean> =
    string("true").map { true } or { string("false").map { false } }
