package me.yapoo.parser

import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess
import me.yapoo.parser.core.string
import org.junit.jupiter.api.Test

class StringParserTest {

    @Test
    fun parseNonEmptyString() {
        string("Hello, ")("Hello, World!") shouldBe ParseSuccess(
            value = "Hello, ",
            rest = "World!"
        )
    }

    @Test
    fun parseEmptyString() {
        string("")("Hello, World!") shouldBe ParseSuccess(
            value = "",
            rest = "Hello, World!"
        )
    }

    @Test
    fun parseFailure() {
        string("Hello, ")("How are you?") shouldBe ParseFailure
    }
}
