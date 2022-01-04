package me.yapoo.parser.core

import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess
import me.yapoo.parser.core.PrimitiveParsers
import org.junit.jupiter.api.Test

class AnyCharParserTest {

    @Test
    fun emptyInput() {
        PrimitiveParsers.anyChar()("") shouldBe ParseFailure
    }

    @Test
    fun singleCharacterInput() {
        PrimitiveParsers.anyChar()("a") shouldBe ParseSuccess(value = 'a', rest = "")
    }

    @Test
    fun multipleCharacterInput() {
        PrimitiveParsers.anyChar()("3.1415926") shouldBe ParseSuccess(value = '3', rest = ".1415926")
    }
}
