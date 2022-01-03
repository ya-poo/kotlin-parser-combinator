package me.yapoo.parser

import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess
import me.yapoo.parser.core.PrimitiveParsers
import org.junit.jupiter.api.Test

class EofParserTest {
    @Test
    fun parseEmptyString() {
        PrimitiveParsers.eof()("") shouldBe ParseSuccess(Unit, "")
    }

    @Test
    fun parseNonEmptyString() {
        PrimitiveParsers.eof()("hoge") shouldBe ParseFailure
    }
}
