package me.yapoo.parser

import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess
import me.yapoo.parser.core.Parsers
import org.junit.jupiter.api.Test

class StringParserTest {

    @Test
    fun parseNonEmptyString() {
        Parsers.string("Hello, ")("Hello, World!") shouldBe ParseSuccess(
            value = "Hello, ",
            rest = "World!"
        )
    }

    @Test
    fun parseEmptyString() {
        Parsers.string("")("Hello, World!") shouldBe ParseSuccess(
            value = "",
            rest = "Hello, World!"
        )
    }

    @Test
    fun parseFailure() {
        Parsers.string("Hello, ")("How are you?") shouldBe ParseFailure
    }
}
