package me.yapoo.parser.core

import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseSuccess
import me.yapoo.parser.core.char
import me.yapoo.parser.core.many
import me.yapoo.parser.core.many1
import org.junit.jupiter.api.Test

class ManyParserTest {
    @Test
    fun manyChars() {
        char('a').many()("aaaaaa") shouldBe ParseSuccess(
            value = listOf('a', 'a', 'a', 'a', 'a', 'a'),
            rest = ""
        )
    }

    @Test
    fun emptyChars() {
        char('a').many()("bbb") shouldBe ParseSuccess(
            value = emptyList(),
            rest = "bbb"
        )
    }

    @Test
    fun many1Chars() {
        char('1').many1()("11") shouldBe ParseSuccess(
            value = listOf('1', '1'),
            rest = ""
        )
    }
}
