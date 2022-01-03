package me.yapoo.parser

val <T> List<T>.head: T
    get() = first()

val <T> List<T>.tail: List<T>
    get() = drop(1)

infix fun <T> T.cons(tail: List<T>): List<T> = listOf(this) + tail
