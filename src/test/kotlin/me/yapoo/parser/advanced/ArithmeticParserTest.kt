package me.yapoo.parser.advanced

import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess
import org.junit.jupiter.api.Test

class ArithmeticParserTest {

    @Test
    fun empty() {
        arithmeticParser()("") shouldBe ParseFailure
    }

    @Test
    fun inputSingleNumber() {
        arithmeticParser()("42") shouldBe ParseSuccess(
            value = 42,
            rest = ""
        )
    }

    @Test
    fun plus() {
        arithmeticParser()("1+2") shouldBe ParseSuccess(
            value = 3,
            rest = ""
        )
    }

    @Test
    fun minus() {
        arithmeticParser()("7-4") shouldBe ParseSuccess(
            value = 3,
            rest = ""
        )
    }

    @Test
    fun multiple() {
        arithmeticParser()("80*4") shouldBe ParseSuccess(
            value = 320,
            rest = ""
        )
    }

    @Test
    fun divide() {
        arithmeticParser()("1024/16") shouldBe ParseSuccess(
            value = 1024 / 16,
            rest = ""
        )
    }

    @Test
    fun `3*(5-2)`() {
        arithmeticParser()("3*(5-2)") shouldBe ParseSuccess(
            value = 3 * (5 - 2),
            rest = ""
        )
    }

    @Test
    fun `42+3*(2-5)`() {
        arithmeticParser()("42+3*(2-5)") shouldBe ParseSuccess(
            value = 42 + 3 * (2 - 5),
            rest = ""
        )
    }
}
