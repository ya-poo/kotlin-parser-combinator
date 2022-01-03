package me.yapoo.parser

import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseSuccess
import me.yapoo.parser.core.Parsers
import me.yapoo.parser.core.many
import me.yapoo.parser.core.many1
import org.junit.jupiter.api.Test

class ManyParserTest {
    @Test
    fun manyChars() {
        Parsers.char('a').many()("aaaaaa") shouldBe ParseSuccess(
            value = listOf('a', 'a', 'a', 'a', 'a', 'a'),
            rest = ""
        )
    }

    @Test
    fun emptyChars() {
        Parsers.char('a').many()("bbb") shouldBe ParseSuccess(
            value = emptyList(),
            rest = "bbb"
        )
    }

    @Test
    fun many1Chars() {
        Parsers.char('1').many1()("11") shouldBe ParseSuccess(
            value = listOf('1', '1'),
            rest = ""
        )
    }
}
